package com.s10plusArtifacts.componets.Buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s10plusArtifacts.utils.Actions
import com.s10plusArtifacts.utils.AligmentEnum
import com.s10plusArtifacts.utils.ComponentProperties
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.HexToJetpackColor
import com.s10plusArtifacts.utils.ScalingUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ButtonImage(
    properties: ComponentProperties,
    action: Actions?,
    onClick: (Actions, DataAnalitic) -> Unit
) {
    val (scaledWidth, scaledHeight) = ScalingUtils.scaleDimensionsToDp(
        width = properties.size.width,
        height = properties.size.height,
        serviceWidth = 400f,
        serviceHeight = 800f
    )

    // Determinar si el diseño es vertical u horizontal
    val isVertical = properties.positionImage == "top" || properties.positionImage == "bottom"

    val alignment = mapAlignment(properties.textAlignment)
     val background = properties.background ?: "#FFFFFF"
    val rounded = properties.cornerRadius?.dp ?: 0.dp

    // Contenedor principal
    if (isVertical) {
        Column(
            modifier = Modifier
                .background(
                    color = HexToJetpackColor.getColor(background),
                    shape = RoundedCornerShape(rounded)
                )
                .width(scaledWidth)
                .height(scaledHeight)
                .clickable {
                    if (action != null) {
                        onClick.invoke(
                            action,
                            DataAnalitic(
                                id = properties.idAnalytics ?: "",
                                action = properties.actionAnalytics ?: ""
                            )
                        )
                    }
                }
                .padding(8.dp),
            horizontalAlignment = alignment.horizontal,
            verticalArrangement = alignment.verticalArrangement
        ) {
            ImageAndText(properties)
        }
    } else {
        Row(
            modifier = Modifier
                .background(
                    color = HexToJetpackColor.getColor(background),
                    shape = RoundedCornerShape(rounded)
                )
                .width(scaledWidth)
                .height(scaledHeight)
                .clickable {
                    if (action != null) {
                        onClick.invoke(
                            action,
                            DataAnalitic(
                                id = properties.idAnalytics ?: "",
                                action = properties.actionAnalytics ?: ""
                            )
                        )
                    }
                }
                .padding(8.dp),
            verticalAlignment = alignment.vertical,
            horizontalArrangement = alignment.horizontalRow
        ) {
            ImageAndText(properties)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageAndText(properties: ComponentProperties) {
    val (scaledWidth, scaledHeight) = if (properties.imageSize != null) {
        ScalingUtils.scaleDimensionsToDp(
            width = properties.imageSize.width,
            height = properties.imageSize.height,
            serviceWidth = 400f, // Ancho de referencia
            serviceHeight = 800f // Alto de referencia
        )
    } else {
        Pair(24.dp, 24.dp) // Tamaño predeterminado de la imagen
    }

    val imageModifier = Modifier
        .width(scaledWidth)
        .height(scaledHeight)

    when (properties.positionImage) {
        "top" -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GlideImage(
                    model = properties.base64Image,
                    contentDescription = "",
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit
                )
                properties.text?.let {
                    Text(
                        text = it,
                        color = if (properties.colorText != null) HexToJetpackColor.getColor(properties.colorText) else Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        "bottom" -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                properties.text?.let {
                    Text(
                        text = it,
                        color = if (properties.colorText != null) HexToJetpackColor.getColor(properties.colorText) else Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                GlideImage(
                    model = properties.base64Image,
                    contentDescription = "",
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit
                )
            }
        }
        "left" -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = properties.base64Image,
                    contentDescription = "",
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit
                )
                properties.text?.let {
                    Text(
                        text = it,
                        color = if (properties.colorText != null) HexToJetpackColor.getColor(properties.colorText) else Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        "right" -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                properties.text?.let {
                    Text(
                        text = it,
                        color = if (properties.colorText != null) HexToJetpackColor.getColor(properties.colorText) else Color.Black,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                GlideImage(
                    model = properties.base64Image,
                    contentDescription = "",
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit
                )
            }
        }
        else -> {
            // Manejo predeterminado: Imagen encima del texto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GlideImage(
                    model = properties.base64Image,
                    contentDescription = "",
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit
                )
                properties.text?.let {
                    Text(
                        text = it,
                        color = if (properties.colorText != null) HexToJetpackColor.getColor(properties.colorText) else Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}



data class AlignmentMapping(
    val horizontal: Alignment.Horizontal, // Para Column
    val vertical: Alignment.Vertical,     // Para Column
    val horizontalRow: Arrangement.Horizontal, // Para Row
    val verticalArrangement: Arrangement.Vertical // Para Column
)

private fun mapAlignment(alignment: String?): AlignmentMapping {
    return when (AligmentEnum.values().find { it.alig == alignment }) {
        AligmentEnum.TOPSTART -> AlignmentMapping(
            horizontal = Alignment.Start,
            vertical = Alignment.Top,
            horizontalRow = Arrangement.Start,
            verticalArrangement = Arrangement.Top
        )
        AligmentEnum.TOPCENTER -> AlignmentMapping(
            horizontal = Alignment.CenterHorizontally,
            vertical = Alignment.Top,
            horizontalRow = Arrangement.Center,
            verticalArrangement = Arrangement.Top
        )
        AligmentEnum.TOPEND -> AlignmentMapping(
            horizontal = Alignment.End,
            vertical = Alignment.Top,
            horizontalRow = Arrangement.End,
            verticalArrangement = Arrangement.Top
        )
        AligmentEnum.MEDIUMSTART -> AlignmentMapping(
            horizontal = Alignment.Start,
            vertical = Alignment.CenterVertically,
            horizontalRow = Arrangement.Start,
            verticalArrangement = Arrangement.Center
        )
        AligmentEnum.MEDIUMCENTER -> AlignmentMapping(
            horizontal = Alignment.CenterHorizontally,
            vertical = Alignment.CenterVertically,
            horizontalRow = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        )
        AligmentEnum.MEDIUMEND -> AlignmentMapping(
            horizontal = Alignment.End,
            vertical = Alignment.CenterVertically,
            horizontalRow = Arrangement.End,
            verticalArrangement = Arrangement.Center
        )
        AligmentEnum.ENDSTART -> AlignmentMapping(
            horizontal = Alignment.Start,
            vertical = Alignment.Bottom,
            horizontalRow = Arrangement.Start,
            verticalArrangement = Arrangement.Bottom
        )
        AligmentEnum.ENDCENTER -> AlignmentMapping(
            horizontal = Alignment.CenterHorizontally,
            vertical = Alignment.Bottom,
            horizontalRow = Arrangement.Center,
            verticalArrangement = Arrangement.Bottom
        )
        AligmentEnum.ENDEND -> AlignmentMapping(
            horizontal = Alignment.End,
            vertical = Alignment.Bottom,
            horizontalRow = Arrangement.End,
            verticalArrangement = Arrangement.Bottom
        )
        else -> AlignmentMapping(
            horizontal = Alignment.CenterHorizontally,
            vertical = Alignment.CenterVertically,
            horizontalRow = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        )
    }
}


