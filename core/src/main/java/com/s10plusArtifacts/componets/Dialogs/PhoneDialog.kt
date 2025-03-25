package com.s10plusArtifacts.componets.Dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.s10plusArtifacts.componets.bases.DialogBase
import com.s10plusArtifacts.coreSdk.R

@Composable
fun PhoneDialog(
    shouldShowDialog: Boolean,
    onDismiss: (String) -> Unit
) {
    val maxChar = 10
    var text by remember { mutableStateOf("") }

    var showError: Boolean by remember {
        mutableStateOf(false)
    }


    DialogBase(
        content = {
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    ) {
                        TextField(value = "+52", onValueChange = {}, enabled = false)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                end = 8.dp, top = 8.dp
                            )
                    ) {
                        TextField(
                            value = text,
                            onValueChange = {
                                if (it.length <= maxChar) text = it
                                showError = false
                            },
                            singleLine = true,
                            label = {
                                Text(text = "Numero telefonico")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            isError = showError,
                            trailingIcon = {
                                if (showError)
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.error),
                                        "error",
                                        tint = Color.Red
                                    )
                            },
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), onClick = {
                            showError = true
                        }) {
                            Text(text = "Cancelar")
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = { onDismiss.invoke(text) },
                            enabled = if (text.length == 10) true else false
                        ) {

                            Text(text = "Aceptar")
                        }
                    }
                }
            }

        },
        onDismissRequest = { },
        shouldShowDialog = shouldShowDialog,
        title = "Bienvenido",
        subTitle = "Para mejorar tu experiencia con la aplicacion apoyanos con tu numero de telefono Gracias !!"
    )

}
