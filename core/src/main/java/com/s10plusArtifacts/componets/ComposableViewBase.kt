package com.s10plusArtifacts.componets

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.s10plusArtifacts.app.presentation.viewmodels.MainViewModel
import com.s10plusArtifacts.componets.Dialogs.DialogModel
import com.s10plusArtifacts.componets.Dialogs.DialogStates
import com.s10plusArtifacts.componets.Dialogs.DialogSuccess
import com.s10plusArtifacts.componets.Dialogs.DialogVideo
import com.s10plusArtifacts.componets.Dialogs.PhoneDialog
import com.s10plusArtifacts.componets.Loader.Loader
import com.s10plusArtifacts.componets.bases.BaseViewModel
import com.s10plusArtifacts.utils.ComponentConvert
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.PermissionHandler
import com.s10plusArtifacts.utils.ScalingUtils
import com.s10plusArtifacts.utils.convertActionIntent
import com.s10plusArtifacts.utils.getArrayPermission


@SuppressLint("UnusedBoxWithConstraintsScope", "SuspiciousIndentation")
@Composable
fun ComposableViewBase(
    arrayElements: MutableList<ComponentConvert> = mutableListOf(),
    viewModel: BaseViewModel,
    onChangeView: (id: String,analitic:DataAnalitic) -> Unit,
) {
    var context = LocalContext.current
    val vm = viewModel
    val vmMain: MainViewModel = hiltViewModel()
    val dialogInformation by vm.dialogInformation.observeAsState(initial = DialogModel())
    val dialofSuccessState by vm.showDialogSuccess.observeAsState(initial = false)
    val dialogVideoState by vm.showDialogVideo.observeAsState(initial = false)
    val urlVideoDialog by vm.urlVideoLiveData.observeAsState(initial = "")
    val onCLickDialogSuccess by vm.onClickDialogSuccess.observeAsState {}
    val showPermission: Boolean by vm.showPermission.observeAsState(initial = false)
    val permissionisGranded: (() -> Unit) by vm.permissionisGranded.observeAsState {}
    val showGetPhoneDialog: Boolean by vm.showDialogGetPhone.observeAsState(initial = false)
    val onClickGetPhoneResponse: ((String) -> Unit) by vm.onClickGetPhone.observeAsState {}
    val showDialogStates: Boolean by vm.showDialogStates.observeAsState(initial = false)
    val onClickSelectetdState: ((Pair<String, String>) -> Unit) by vm.onClickSelectedState.observeAsState {}
    var heightIs by remember { mutableFloatStateOf(0f) }
    var whitIs by remember { mutableFloatStateOf(0f) }
    val isLoading by viewModel.isLoading.collectAsState()
    val maxWidth = arrayElements.maxOfOrNull { item ->
        val (scaledX, _) = ScalingUtils.scaleCoordinatesToDp(
            x = item.properties.position.x,
            y = 0f,
            serviceWidth = 400f,
            serviceHeight = 800f
        )
        val (scaledWidth, _) = ScalingUtils.scaleDimensionsToDp(
            width = item.properties.size.width,
            height = 0f,
            serviceWidth = 400f,
            serviceHeight = 800f
        )
        scaledX + scaledWidth
    } ?: 0.dp
    val maxHeight = arrayElements.maxOfOrNull { item ->
        val (_, scaledY) = ScalingUtils.scaleCoordinatesToDp(
            x = 0f,
            y = item.properties.position.y,
            serviceWidth = 400f,
            serviceHeight = 800f
        )
        val (_, scaledHeight) = ScalingUtils.scaleDimensionsToDp(
            width = 0f,
            height = item.properties.size.height,
            serviceWidth = 400f,
            serviceHeight = 800f
        )
        scaledY + scaledHeight
    } ?: 0.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)

        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = maxWidth) // Usa widthIn para evitar problemas de tamaño
                    .heightIn(min = maxHeight) // Usa heightIn para alturas dinámicas
            ) {

                arrayElements.forEach { item ->

                    val context = LocalContext.current

                    val (scaledX, scaledY) = ScalingUtils.scaleCoordinatesToDp(
                        item.properties.position.x,
                        item.properties.position.y,
                        400f,
                        800f
                    )

                    val (scaledWidth, scaledHeight) = ScalingUtils.scaleDimensionsToDp(
                        width = item.properties.size.width,
                        height = item.properties.size.height,
                        serviceWidth = 400f,
                        serviceHeight = 800f
                    )

                    Row(
                        modifier = Modifier
                            .offset(x = scaledX, y = scaledY)
                            .width(scaledWidth) // Ancho dinámico
                            .height(scaledHeight) // Altura dinámica
                            .clickable {
                                if (item.actions != null) {
                                    if (item.actions.openSections.isNotEmpty()) {
                                        onChangeView(
                                            item.actions.openSections,
                                            DataAnalitic(
                                                item.properties.idAnalytics,
                                                item.properties.actionAnalytics
                                            )
                                        )
                                        //vmMain.changeView(item.actions.openSections)
                                    } else {

                                        var intent = convertActionIntent(item.actions)
                                        if (intent != null) {
                                            context.startActivity(intent)
                                        }
                                    }
                                }
                            }

                    ) {
                        item.component.invoke() { action,analitic ->
                            if (item.actions != null) {
                                if (item.actions.openSections.isNotEmpty()) {

                                    onChangeView(item.actions.openSections,analitic)

                                    ///
                                } else {

                                    var intent = convertActionIntent(item.actions)
                                    if (intent != null) {
                                        context.startActivity(intent)
                                    }
                                    onChangeView("",analitic)

                                }
                            }
                        }
                    }

                }
                DialogSuccess(
                    title = dialogInformation.title,
                    subTitle = dialogInformation.subTitle,
                    txtButton = dialogInformation.txtButton1,
                    shouldShowDialog = dialofSuccessState,
                    onDismissRequest = {

                    }) {
                    onCLickDialogSuccess.invoke()
                    Log.i("", "")
                }
                if (showPermission) {
                        PermissionHandler(
                            permissions = getArrayPermission(),
                            askPermission = true
                        ) { result ->
                            if (result.all { it.value }) {
                                permissionisGranded.invoke()
                            } else {
                                // Muestra un mensaje o maneja el caso de denegación permanente
                                vm.showDialogSuccess("Permiso necesario", "Para continuar, por favor otorga todos los permisos requeridos.","aceptar") {
                                    vm.closDialogAll()
                                }
                            }
                        }
                    }

                }

                    if(urlVideoDialog.isNotEmpty()) {
                        DialogVideo(
                            urlVideo = urlVideoDialog,
                            shouldShowDialog = dialogVideoState
                        ) {
                                vm.closDialogAll()
                        }
                    }

                PhoneDialog(showGetPhoneDialog) { phone ->
                    onClickGetPhoneResponse.invoke(phone)
                }


                DialogStates(
                    showDialogStates
                ) { itemSelected ->
                    onClickSelectetdState.invoke(itemSelected)
                }


            }


    Loader(isLoading = isLoading, modifier = Modifier.fillMaxSize())

}
