package com.s10plusArtifacts.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.s10plusArtifacts.componets.Dialogs.DialogModel
import com.s10plusArtifacts.componets.Dialogs.DialogStates
import com.s10plusArtifacts.componets.Dialogs.DialogSuccess
import com.s10plusArtifacts.componets.Dialogs.PhoneDialog
import com.s10plusArtifacts.componets.bases.BaseViewModel
import com.s10plusArtifacts.utils.PermissionHandler
import com.s10plusArtifacts.utils.getArrayPermission

@Composable
fun BaseViewSingleComponent(viewModel: BaseViewModel,component: @Composable () -> Unit) {
    var context = LocalContext.current
    val vm = viewModel
    val dialogInformation by vm.dialogInformation.observeAsState(initial = DialogModel())
    val dialofSuccessState by vm.showDialogSuccess.observeAsState(initial = false)
    val onCLickDialogSuccess by vm.onClickDialogSuccess.observeAsState {}
    val showPermission: Boolean by vm.showPermission.observeAsState(initial = false)
    val permissionisGranded: (() -> Unit) by vm.permissionisGranded.observeAsState {}
    val showGetPhoneDialog: Boolean by vm.showDialogGetPhone.observeAsState(initial = false)
    val onClickGetPhoneResponse: ((String) -> Unit) by vm.onClickGetPhone.observeAsState {}
    val showDialogStates: Boolean by vm.showDialogStates.observeAsState(initial = false)
    val onClickSelectetdState: ((Pair<String, String>) -> Unit) by vm.onClickSelectedState.observeAsState {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()


        ) {
            component.invoke()
        }
        DialogSuccess(
            title = dialogInformation.title,
            subTitle = dialogInformation.subTitle,
            txtButton = dialogInformation.txtButton1,
            shouldShowDialog = dialofSuccessState,
            onDismissRequest = {

            }) {
            onCLickDialogSuccess.invoke()
        }
        if (showPermission) {
            PermissionHandler(
                permissions = getArrayPermission(), askPermission = true
            ) { result ->
                var count: Int = 0
                result.forEach { s, b ->
                    if (!b) {
                        vm.showDialogSuccess("¡Atencion!", "", "") {
                            vm.closDialogAll()
                        }
                    } else {
                        count++
                    }
                }
                if (count == result.size) {
                    permissionisGranded.invoke()
                }

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

}