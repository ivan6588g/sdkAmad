package com.s10plusArtifacts.componets.Buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.s10plusArtifacts.utils.Actions
import com.s10plusArtifacts.utils.ComponentProperties
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.HexToJetpackColor
import com.s10plusArtifacts.utils.ScalingUtils
import com.s10plusArtifacts.utils.getAligment

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ButtonNs(propertiesButtonComposable: ComponentProperties,actions:Actions?, onClick: (Actions, DataAnalitic) -> Unit) {
    var context = LocalContext.current

    val (scaledWidth, scaledHeight) = ScalingUtils.scaleDimensionsToDp(
        width = propertiesButtonComposable.size.width, // Ancho original
        height = propertiesButtonComposable.size.height, // Alto original
        serviceWidth = 400f, // Ancho de referencia
        serviceHeight = 800f // Alto de referencia
    )
    Box(

    ) {
        Button(
            modifier = Modifier
                .height(scaledHeight)
                .width(scaledWidth),
            onClick = {
                actions?.let { onClick.invoke(it,
                    DataAnalitic(id = propertiesButtonComposable.idAnalytics ?: "", action = propertiesButtonComposable.actionAnalytics ?: "")
                ) }
            },
            colors = ButtonColors(
                contentColor = HexToJetpackColor.getColor(propertiesButtonComposable.background ?: "#FFFFF"),
                disabledContentColor = HexToJetpackColor.getColor(propertiesButtonComposable.background ?: "#FFFFF"),
                containerColor = HexToJetpackColor.getColor(propertiesButtonComposable.background ?: "#FFFFF"),
                disabledContainerColor = HexToJetpackColor.getColor(propertiesButtonComposable.background ?: "#FFFFF"),

                ),
            shape = RoundedCornerShape(propertiesButtonComposable.cornerRadius ?: 0),
            contentPadding = PaddingValues(0.dp) // ❗ Elimina el margen interno del botón

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = getAligment(propertiesButtonComposable.textAlignment ?: "MC")){
                    Text(
                        text = propertiesButtonComposable.text ?: "",
                        color = HexToJetpackColor.getColor(propertiesButtonComposable.colorText ?: "#FFFFFF"),
                        fontSize = (propertiesButtonComposable.fontSize ?: 12).sp,
                        maxLines = 1,

                        )

                }
            }



        }
    }

}


