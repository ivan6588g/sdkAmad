package com.s10plusArtifacts.utils

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.TelecomManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.s10plusArtifacts.coreSdk.BuildConfig


object PermissionUtils {

    private lateinit var  requestPermissionLauncher: ActivityResultLauncher<Array<String>>
     var onComplete:(()->Unit)?=null
     var onFail:(()->Unit)?=null
    var activity : ComponentActivity ? =null
    private var failPermission:ArrayList<String> = arrayListOf()

    private var contIntent =0
    private var totalIntent =2
    private val permission = arrayListOf(
      /*  Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,*/
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun init(activity : AppCompatActivity) {
        this.activity = activity
        requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            failPermission.clear()
            it.entries.forEach { item ->
                if (!item.value) {
                    failPermission.add(item.key)
                }
            }
            if(failPermission.count()!=0){
                contIntent++
                if(contIntent ==totalIntent){
                    onComplete?.invoke()
                    contIntent=0
                }
                else{
                    onFail?.invoke()

                }
            }else {
                onComplete?.invoke()
            }

        }
    }

    fun  requestPermission(){

         if (BuildConfig.BUILD_TYPE == "debug" || BuildConfig.BUILD_TYPE == "qa") {
             permission.add(Manifest.permission.READ_CALL_LOG)
         }

         if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
             permission.add(Manifest.permission.ANSWER_PHONE_CALLS)
         } else {
             permission.add(Manifest.permission.READ_PHONE_STATE)

         }

        requestPermissionLauncher.launch(permission.toTypedArray(), ActivityOptionsCompat.makeBasic())


    }

    @SuppressLint("QueryPermissionsNeeded")
    @TargetApi(Build.VERSION_CODES.M)
    fun launchSetDefaultDialerIntent(activity: AppCompatActivity) {
        Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).putExtra(
            TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
            activity.packageName
        ).apply {
            if (resolveActivity(activity.packageManager) != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val rm: RoleManager? = activity.getSystemService(RoleManager::class.java)
                    if (rm?.isRoleAvailable(RoleManager.ROLE_DIALER) == true) {
                        @Suppress("DEPRECATION")
                        activity.startActivityForResult(
                            rm.createRequestRoleIntent(RoleManager.ROLE_DIALER),
                            12344
                        )
                    }
                } else {
                    @Suppress("DEPRECATION")
                    activity.startActivityForResult(this, 12344)
                }
            } else {
                Toast.makeText(activity,"No reconocido", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun  rentedRequestPermission(permission: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    activity!!,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
                activity!!.shouldShowRequestPermissionRationale(permission) -> {


            }
                else -> {
                    requestPermissionLauncher.launch(failPermission.toTypedArray())
                }
            }
        }
    }

}