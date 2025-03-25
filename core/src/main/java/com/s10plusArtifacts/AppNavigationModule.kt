package com.s10plusArtifacts

import android.content.Context
import android.content.Intent

object AppNavigationModule {
    fun open(context: Context,nameModule:String,clearActivity:Boolean = false) =
        context.startActivity(
            intentClearTop(InternalIntent(context,nameModule),clearActivity)
        )

    fun openParamToken(context: Context,nameModule: String,token:String,clearActivity: Boolean = false,backView:Boolean) = context.startActivity(
        intentClearTop(internalIntentParams(
            context,nameModule,token,backView
        ),clearActivity)
    )

    private fun intentClearTop(intent: Intent,clearActivity: Boolean): Intent =
        intent.apply {
            flags = if(clearActivity){
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }else {
                Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }

    private  fun internalIntentParams(context: Context,action: String,extra: String,navView:Boolean):Intent{
        var intent = Intent(action).setPackage(context.packageName)
        intent.putExtra("token",extra)
        intent.putExtra("navView",navView)
        return intent
    }

    private fun InternalIntent(context: Context,action:String) =
        Intent(action).setPackage(context.packageName)

    fun OpenMain(context: Context,clearActivity: Boolean = false,token:String,backView: Boolean = false) = openParamToken(
        context,
        MAIN_ACTIVITY,
        token,
        clearActivity,
        backView


    )

    fun OpenChange(context: Context,clearActivity: Boolean = false) = open(
        context,
        CHANGEVIEW,
        clearActivity

    )

    val MAIN_ACTIVITY = "com.s10plus.amad.qa.OPEN_MAIN"
    val CHANGEVIEW = "com.s10plus.login.LoginActivity"
}