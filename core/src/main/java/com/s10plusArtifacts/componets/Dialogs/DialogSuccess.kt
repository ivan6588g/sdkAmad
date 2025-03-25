package com.s10plusArtifacts.componets.Dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.s10plusArtifacts.componets.bases.DialogBase
import com.s10plusArtifacts.coreSdk.R

@Composable
fun DialogSuccess(
    title:String,
    subTitle:String,
    txtButton:String,
    shouldShowDialog:Boolean,
    onDismissRequest: ()->Unit,
    onClickSuccess:()->Unit
){
    DialogBase(
        onDismissRequest = { onDismissRequest.invoke()},
        shouldShowDialog = shouldShowDialog,
        title = title ,
        subTitle =  subTitle,
        icon = R.raw.success_lottie,
        content = {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp), contentAlignment = Alignment.Center){
            Button(onClick = {
                onClickSuccess.invoke()
            }) {
                Text(text = txtButton)
            }
                }
        }
    )

}