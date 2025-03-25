package com.s10plusArtifacts.app.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.s10plusArtifacts.app.domain.entities.TokenRequestModel
import com.s10plusArtifacts.app.domain.model.RequestAnalytics
import com.s10plusArtifacts.app.domain.model.RequestClick
import com.s10plusArtifacts.app.usecase.ClickUseCase
import com.s10plusArtifacts.app.usecase.GetAllStateSepo
import com.s10plusArtifacts.app.usecase.GetColoniasUseCase
import com.s10plusArtifacts.app.usecase.GetMunicipiosUseCase
import com.s10plusArtifacts.app.usecase.LoadUseCase
import com.s10plusArtifacts.componets.bases.BaseViewModel
import com.s10plusArtifacts.extensions.launch
import com.s10plusArtifacts.extensions.onResult
import com.s10plusArtifacts.sesion.SessionDelegateCore
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.PersonalInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PersonalInformationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionDelegate: SessionDelegateCore,
    private val getAllStateSepo: GetAllStateSepo,
    private val loadUseCase: LoadUseCase,
    private val sendAnalitck: ClickUseCase,
    private val getMunicipiosUseCase: GetMunicipiosUseCase,
    private val getColoniasUseCase: GetColoniasUseCase
) : BaseViewModel(), SessionDelegateCore by sessionDelegate {

    private val _dataInformation = MutableLiveData<PersonalInformation?>(null)
    val dataInformation: LiveData<PersonalInformation?> = _dataInformation
    private val _dataShowList = MutableLiveData<List<String>>()
    val dataShowList: LiveData<List<String>> = _dataShowList

    private val _nameList = MutableLiveData<String>()
    val nameList: LiveData<String> = _nameList

    private val _changeScreen = MutableLiveData<Boolean?>(null)
    val changeScreen: LiveData<Boolean?> = _changeScreen

    var typeState = 0



    fun getInformationPerson() {
        _isLoading.value = true
        val gson = Gson()
        var personalInformationJson = sessionDelegate.personalInfo
        //var dataInformationConvert = gson.fromJson(personalInformationJson,PersonalInformation::class.java)
        _dataInformation.value = personalInformationJson
        if(personalInformationJson?.showTypesData?.size!! <= 0){
            setLoadinfo("")
        }
        else{
            _dataInformation.value = personalInformationJson

            if (personalInformationJson?.locationInformation?.neighborhoods != null) {

            } else if (personalInformationJson?.locationInformation?.municipality != null) {
                getColoniasSepo(personalInformationJson.locationInformation.municipality.id_municipio.toString())
                _nameList.value = " una colonia"
                typeState = 2
            } else if (personalInformationJson?.locationInformation?.state != null) {
                getMunicipiosSepo(personalInformationJson.locationInformation.state.id_estado.toString())
                _nameList.value = "un municipio"
                typeState = 3
            } else {
                getStateSepo()
                _nameList.value = "un estado"
                typeState = 1
            }
        }
        Log.i("", "")
    }

    fun getStateSepo() {
        launch {
            getAllStateSepo.invoke("").onResult({
                Log.i("", "")
                var lisTemp = mutableListOf<String>()
                it?.forEach {
                    lisTemp.add(it.estado)
                }
                _dataShowList.value = lisTemp.sorted()
                _isLoading.value = false
            }, {
                Log.i("", "")
            })
        }
    }

    fun getMunicipiosSepo(idState: String) {
        launch {
            getMunicipiosUseCase.invoke(idState).onResult({
                var lisTemp = mutableListOf<String>()
                it?.forEach {
                    lisTemp.add(it.municipio)
                }
                _dataShowList.value = lisTemp.sorted()
                _isLoading.value = false
            }, {
                Log.i("", "")
            })
        }
    }

    fun getColoniasSepo(idMunicipio: String) {
        launch {
            getColoniasUseCase.invoke(idMunicipio).onResult({
                var lisTemp = mutableListOf<String>()
                it?.forEach {
                    lisTemp.add(it.colonia)
                }
                _isLoading.value = false
                _dataShowList.value = lisTemp.sorted()
            }, {
                Log.i("", "")
            })
        }
    }

    fun setLoadinfo(selected: String) {
        _isLoading.value = true
        var serial = sessionDelegate.serial
        var tokenRequestModel = TokenRequestModel()
        tokenRequestModel.idApplication = sessionDelegate.idApp
        if (sessionDelegate.serial.isEmpty()) {
            Log.i("", "")
            sessionDelegate.serial = UUID.randomUUID().toString().substring(0, 15)
        }
        //tokenRequestModel.otherInformation.state = selected

        if (sessionDelegate.state.isNotEmpty()) {
            val type = object : TypeToken<Pair<String?, String>>() {}.type
            val testHashMap2: Pair<String, String> =
                Gson().fromJson(sessionDelegate.state, type)
            tokenRequestModel.otherInformation.state = testHashMap2.second
        }
        when (typeState){
            1->{
                tokenRequestModel.otherInformation.state = selected
            }
            2->{
                tokenRequestModel.otherInformation.colonia = selected
            }
            3->{
                tokenRequestModel.otherInformation.municipio = selected
            }
        }

        tokenRequestModel.phoneId = sessionDelegate.serial
        tokenRequestModel.phoneNumber = ""
        tokenRequestModel.otherInformation.lat = if(sessionDelegate.lat.isNotEmpty() || !sessionDelegate.lng.isNullOrBlank()){sessionDelegate.lat.toDouble()}else{0.0}
        tokenRequestModel.otherInformation.long = if(sessionDelegate.lng.isNotEmpty() || !sessionDelegate.lng.isNullOrBlank()) {sessionDelegate.lng.toDouble()}else{0.0}

        if (sessionDelegate.phone.isEmpty()) {
            tokenRequestModel.otherInformation.telMarcado = "5511620300"
        } else {
            tokenRequestModel.otherInformation.telMarcado = sessionDelegate.phone
        }
        launch {
            loadUseCase.invoke(tokenRequestModel).onResult(
                {
                    if (it != null) {
                        Log.i("error", "")
                        sessionDelegate.tokenClick = it
                        _changeScreen.value = true
                        sendAnalitc(DataAnalitic(analiticOpen,"opened"))                    }
                }, {
                    Log.i("error", "")
                }
            )
        }
    }

    fun sendAnalitc(analitc: DataAnalitic){
        if(analitc.id?.isNotEmpty() == true) {
            launch {
                sendAnalitck.invoke(
                    RequestClick(
                        token = tokenClick,
                        RequestAnalytics(
                            idAction = analitc.id ?: "",
                            otherInformation = analitc.action ?: ""
                        )
                    )
                ).onResult({
                    Log.i("", "${it}")
                    //tokenClick = it
                }, {
                    Log.i("", "${it}")

                })
            }
        }
    }
}