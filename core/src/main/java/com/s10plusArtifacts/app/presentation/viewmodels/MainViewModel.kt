package com.s10plusArtifacts.app.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.s10plusArtifacts.app.domain.model.RequestAnalytics
import com.s10plusArtifacts.app.domain.model.RequestClick
import com.s10plusArtifacts.app.usecase.ClickUseCase
import com.s10plusArtifacts.app.usecase.GetConfigurationUseCase
import com.s10plusArtifacts.componets.Audio.AudioPlayer
import com.s10plusArtifacts.componets.bases.BaseViewModel
import com.s10plusArtifacts.extensions.launch
import com.s10plusArtifacts.extensions.onResult
import com.s10plusArtifacts.sesion.SessionDelegateCore
import com.s10plusArtifacts.utils.ComponentConvert
import com.s10plusArtifacts.utils.ComponentProperties
import com.s10plusArtifacts.utils.Configuration
import com.s10plusArtifacts.utils.ConvertComponent
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.ImageSize
import com.s10plusArtifacts.utils.Margin
import com.s10plusArtifacts.utils.Position
import com.s10plusArtifacts.utils.ReadJSONFromAssets
import com.s10plusArtifacts.utils.Size
import com.s10plusArtifacts.utils.View
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val sendAnalitck:ClickUseCase,
    private val sessionDelegate: SessionDelegateCore
): BaseViewModel(), SessionDelegateCore by sessionDelegate {
    private val _componentsFromJson = MutableLiveData<MutableList<ComponentConvert>>()
    var componentsFromJson: LiveData<MutableList<ComponentConvert>> = _componentsFromJson


    // Stack para manejar el historial de vistas
    private val _viewStack = MutableStateFlow<List<String>>(listOf())
    val viewStack: StateFlow<List<String>> = _viewStack

/*
    fun getView(){
        val gson = Gson()
        var listView = sessionDelegate.views
        if(views.isNotEmpty()){
            var arrayViews = gson.fromJson(views,Array<View>::class.java)
            arrayViews.forEach { view ->
                if(view.mainView){
                    var convert =  ConvertComponent(view.component)
                    if(urlSound.isNotEmpty()){
                        convert.add( ComponentConvert(
                            ComponentProperties(
                                position = Position(0f, 0f),
                                text = "",
                                fontSize = 0,
                                colorText = "",
                                size = Size(height = 0f, width = 0f),
                                cornerRadius = 0,
                                itemCarousel = listOf(),
                                textAlignment = "",
                                background = "",
                                base64Image = "",
                                margin = Margin(top = 0, bottom = 0, left = 0, right = 0),
                                iconColor = "",
                                iconName = "",
                                actionAnalytics = "",
                                idAnalytics = "",
                                imageSize = ImageSize(0f,0f),
                                positionImage = ""

                            ), actions = null, component = {
                                AudioPlayer(url = "${urlSound}")
                            }))
                    }
                    _componentsFromJson.value = convert
                    // Agregar la vista principal al stack
                    _viewStack.value = listOf(view.id)
                }
            }

        }
    }
*/

    fun getView() {
        val gson = Gson()
        // Ejecutar en un hilo secundario para evitar bloqueos en la interfaz
        viewModelScope.launch (Dispatchers.IO) {

            if (views.isNotEmpty()) {
                try {
                    val arrayViews = gson.fromJson(views, Array<View>::class.java)
                    val mainView = arrayViews.firstOrNull { it.mainView }

                    mainView?.let { view ->
                        val convert = ConvertComponent(view.component)

                        if (urlSound.isNotEmpty()) {
                            // Añadir componente del reproductor de audio
                            convert.add(
                                ComponentConvert(
                                    ComponentProperties(
                                        position = Position(0f, 0f),
                                        text = "",
                                        fontSize = 0,
                                        colorText = "",
                                        size = Size(height = 0f, width = 0f),
                                        cornerRadius = 0,
                                        itemCarousel = listOf(),
                                        textAlignment = "",
                                        background = "",
                                        base64Image = "",
                                        margin = Margin(top = 0, bottom = 0, left = 0, right = 0),
                                        iconColor = "",
                                        iconName = "",
                                        actionAnalytics = "",
                                        idAnalytics = "",
                                        imageSize = ImageSize(0f, 0f),
                                        positionImage = "", videoURL = ""
                                    ),
                                    actions = null,
                                    component = {
                                        AudioPlayer(url = "${urlSound}")
                                    }
                                )
                            )
                        }
                        if(welcomeVideo.isNotEmpty()){
                            showDialogSuccessModal(welcomeVideo)
                        }

                        // Actualizar los estados en el hilo principal
                        withContext(Dispatchers.Main) {
                            _componentsFromJson.value = convert
                            _viewStack.value = listOf(view.id)
                        }
                    }
                } catch (e: Exception) {
                    // Manejar posibles errores de deserialización
                    e.printStackTrace()
                }
            }
        }
    }


    fun changeView(id:String){
        _componentsFromJson.value = arrayListOf()
        val gson = Gson()
        var listView = sessionDelegate.views
        if(views.isNotEmpty()){
            var arrayViews = gson.fromJson(views,Array<View>::class.java)
            arrayViews.forEach { view ->
                if(id != "0") {
                    if (view.id == id) {
                        var convert = ConvertComponent(view.component)
                        _componentsFromJson.value = convert
                        // Agregar la nueva vista al stack
                        if (!view.mainView) {
                            _viewStack.value += id
                        }
                    }
                }else {
                    if(view.mainView){
                        var convert = ConvertComponent(view.component)
                        _componentsFromJson.value = convert
                        // Agregar la nueva vista al stack
                        if (!view.mainView) {
                            _viewStack.value += id
                        }
                    }
                }
            }

        }
    }

    fun GetConfig() {
        launch {
            getConfigurationUseCase.invoke(sessionDelegate.getId()).onResult(
                {
                    if (it != null) {
                        setPreConfiguration(it.preconfiguration)
                        setViews(it.views)

                    }
                }, {
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

    // Función para regresar a la vista anterior
    fun popView() {
        if (_viewStack.value.size > 1) {
            // Eliminar la vista actual del stack
            _viewStack.value = _viewStack.value.dropLast(1)

            // Actualizar los componentes con la vista anterior
            val previousViewId = _viewStack.value.last()
            changeView(previousViewId)
        }
    }

    fun sendAnalitc(analitc:DataAnalitic){
        launch {
            sendAnalitck.invoke(RequestClick(token = tokenClick, RequestAnalytics(idAction = analitc.id ?: "", otherInformation = analitc.action ?: ""))).onResult({
                Log.i("","${it}")
                //tokenClick = it
            },{
                Log.i("","${it}")

            })
        }
    }
}