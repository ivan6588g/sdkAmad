package com.s10plusArtifacts.app.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.s10plusArtifacts.app.presentation.viewmodels.MainViewModel
import com.s10plusArtifacts.componets.ComposableViewBase
import com.s10plusArtifacts.componets.Loader.Loader
import com.s10plusArtifacts.utils.ComponentConvert

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainView(viewmodel: MainViewModel = hiltViewModel(), goToChangeView: (id: String) -> Unit) {
    val list: MutableList<ComponentConvert> by viewmodel.componentsFromJson.observeAsState(initial = mutableListOf())
    val viewStack by viewmodel.viewStack.collectAsState()
    var isScreenReady by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = list) {
        if(list.isNotEmpty()){
            isScreenReady = true
        }
    }


    // Mostrar Loader mientras no esté lista la pantalla
    if (!isScreenReady) {
       Loader(isLoading = true, modifier = Modifier.fillMaxSize())
    } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Row {
                    ComposableViewBase(arrayElements = list, viewModel = viewmodel) { id,analitc ->
                        if(id.isNotEmpty()) {
                            viewmodel.changeView(id)
                        }
                        if(analitc.id?.isNotEmpty() == true){
                            viewmodel.sendAnalitc(analitc)
                        }
                    }
                }
            }
    }
    // Simula procesos que tardan tiempo (puede ser una llamada a un servicio o conversión)
    LaunchedEffect(Unit) {
        viewmodel.getView()
        //isScreenReady = true // Indica que la pantalla está lista para mostrarse
    }

    BackHandler {
        viewmodel.changeView("0")
    }

}
