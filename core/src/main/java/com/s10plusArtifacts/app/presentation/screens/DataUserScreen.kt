package com.s10plusArtifacts.app.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s10plusArtifacts.app.presentation.navigation.BaseRoutes
import com.s10plusArtifacts.app.presentation.viewmodels.PersonalInformationViewModel
import com.s10plusArtifacts.componets.DropDown.DropdownComponent
import com.s10plusArtifacts.componets.Loader.Loader
import com.s10plusArtifacts.utils.PersonalInformation

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DataUserScreen(
    viewmodel: PersonalInformationViewModel = hiltViewModel(),
    onChange: (screen: BaseRoutes) -> Unit
) {
    var selected by remember { mutableStateOf("") }
    val textFieldValues = remember { mutableStateMapOf<String, String>() }
    val showInfoState:List<String> by viewmodel.dataShowList.observeAsState(initial = mutableListOf())
    val dataInformation: PersonalInformation? by viewmodel.dataInformation.observeAsState(initial = null)
    val nameList:String by viewmodel.nameList.observeAsState(initial = "")
    val changeViewModel by viewmodel.changeScreen.observeAsState(initial = null)
    val errors = remember { mutableStateMapOf<String, String>() }
    val isLoading by viewmodel.isLoading.collectAsState() // Observar el estado de carga

    LaunchedEffect(key1 = "") {
        viewmodel.getInformationPerson()
    }

    LaunchedEffect(key1 = changeViewModel) {
        if(changeViewModel == true){
            viewmodel._isLoading.value = false
            onChange.invoke(BaseRoutes.MainScreen)
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(bottom =  WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    ) {
        if (dataInformation != null) {
            Column(modifier = Modifier.fillMaxSize()) {

                if (!dataInformation?.urlImage.isNullOrBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            model = dataInformation!!.urlImage,
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (!dataInformation?.title.isNullOrBlank()) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = dataInformation!!.title, textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }
                dataInformation?.showTypesData?.forEach { name ->
                    when (name) {
                        "Localizacion" -> {
                            DropdownComponent(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                options = showInfoState,
                                label = "Selecciona ${nameList} "
                            ) { selectedOption ->
                                selected = selectedOption
                                textFieldValues["Localizacion"] =
                                    selectedOption // Guardar selección
                                errors["Localizacion"] = ""

                            }
                            if (errors["Localizacion"].isNullOrBlank().not()) {
                                Text(
                                    text = errors["Localizacion"]!!,
                                    color = Color.Red,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }

                        else -> {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                var fieldValue by remember { mutableStateOf("") }

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    value = fieldValue,
                                    onValueChange = { newValue ->
                                        fieldValue = newValue
                                        textFieldValues[name] = newValue // Guardar valor del campo

                                    },
                                    label = { Text(text = name) },
                                    isError = errors[name]?.isNotEmpty() == true

                                )
                            }

                            if (errors[name]?.isNotEmpty() == true) {
                                Text(
                                    text = errors[name]!!,
                                    color = Color.Red,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de siguiente
                if (dataInformation?.showTypesData?.size!! > 0) {

                    Button(
                        onClick = {
                            errors.clear()
                            val collectedData = textFieldValues.toMap()

                            // Validar campos obligatorios
                            var hasError = false
                            dataInformation?.showTypesData?.forEach { name ->
                                if (collectedData[name].isNullOrBlank()) {
                                    errors[name] = "Este campo es obligatorio"
                                    hasError = true
                                }
                            }

                            if (!hasError) {
                                println("Datos recopilados: $collectedData")
                                viewmodel.setLoadinfo(selected)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = "Siguiente", color = Color.White)
                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                    Text(text = "v1.0.1")
                }
            }

        } else {
        }
        // Loader superpuesto
        Loader(isLoading = isLoading, modifier = Modifier.fillMaxSize())
    }



}
