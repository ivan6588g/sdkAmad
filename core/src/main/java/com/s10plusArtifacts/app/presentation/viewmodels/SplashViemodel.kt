package com.s10plusArtifacts.app.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.s10plusArtifacts.app.usecase.GetAllStateSepo
import com.s10plusArtifacts.app.usecase.GetConfigurationUseCase
import com.s10plusArtifacts.componets.bases.BaseViewModel
import com.s10plusArtifacts.extensions.launch
import com.s10plusArtifacts.extensions.onResult
import com.s10plusArtifacts.sesion.SessionDelegateCore
import com.s10plusArtifacts.utils.Configuration
import com.s10plusArtifacts.utils.NetworkConneccionChecked
import com.s10plusArtifacts.utils.PersonalInformation
import com.s10plusArtifacts.utils.ReadJSONFromAssets
import com.s10plusArtifacts.utils.getCurentLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViemodel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val getState: GetAllStateSepo,
    private val sessionDelegate: SessionDelegateCore
) : BaseViewModel(), SessionDelegateCore by sessionDelegate {
    var TAG = "SPLASH VIEMODEL"
    private  val PREF_FILE = "master_key_preference"

    private val _complete = MutableLiveData<Boolean>()
    var complete: LiveData<Boolean> = _complete

    private val _showInformationPersonal = MutableLiveData<Boolean>(null)
    var showInformationPersonal: LiveData<Boolean> = _showInformationPersonal

    private var _personalInfo = MutableLiveData<PersonalInformation?>()
    val personalInformation: LiveData<PersonalInformation?> = _personalInfo

    init {
        _personalInfo.value = null
        _isLoading.value = true
    }

    fun setTokenValidate(token: String) {
        createSession(token)
    }

    fun validateSession(): Boolean {
        return sessionDelegate.getId().isNotEmpty()
    }

    fun stepOne() {

        if (sessionDelegate.isFirstSesion) {
            Log.i("", "")
            if (NetworkConneccionChecked(context)) {
                Log.i("", "")
                checkPermission()
            } else {
                showDialogSuccess(
                    title = "Activa tu conexion a internet",
                    subTitle = "¡Es necesario la conexion a internet cuando inicia por primera vez la aplicacíon, para descargar las configuraciones necesarias!",
                    onClick = {
                        closDialogAll()
                        stepOne()
                    }, btnTitle = "Reintentar"
                )
            }
        } else {
            checkPermission()
        }
    }

    fun checkPermission() {
        showPermission(true) {
            getCurentLocation({ location ->
                sessionDelegate.lat = location.first.toString()
                sessionDelegate.lng = location.second.toString()
                GetConfig()
            }, { exeption ->
                Log.i("", "")
                GetConfig()
            }, context = context)
        }
    }


    fun GetLocalConfig() {
        val gson = Gson()
        val json = ReadJSONFromAssets(context, "data.json")
        val test = gson.fromJson(json, Configuration::class.java)
        setPreConfiguration(test.preconfiguration)
        setViews(test.views)

        status = test.status
        if (test.personalInformation != null) {
            _personalInfo.value = test.personalInformation
            updatePersonalInfo(test.personalInformation)
            _showInformationPersonal.value = test.personalInformation != null
        }
        _complete.value = true

    }

    fun GetConfig() {
        launch {
            getConfigurationUseCase.invoke(sessionDelegate.getId()).onResult(
                {
                    if (it != null) {

                        sessionDelegate.idApp = sessionDelegate.getId()
                        setPreConfiguration(it.preconfiguration)
                        setViews(it.views)

                        isFirstSesion = false
                        status = it.status

                        if (it.personalInformation != null) {

                                _personalInfo.value = it.personalInformation
                                updatePersonalInfo(it.personalInformation)
                                _showInformationPersonal.value = it.personalInformation.showTypesData.isNotEmpty()

                        }
                               _isLoading.value = false

                    }
                },
                {
                    val gson = Gson()
                    val json = ReadJSONFromAssets(context, "data.json")
                    val test = gson.fromJson(json, Configuration::class.java)
                    setPreConfiguration(test.preconfiguration)
                    setViews(test.views)
                    status = test.status

                }
            )
        }


    }


}