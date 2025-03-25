package com.s10plusArtifacts.app.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun FindWindowScreen() {

    val configuration = LocalConfiguration.current
    val heightDP = configuration.screenHeightDp
    val widthDP = configuration.screenWidthDp

    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val widthPx = displayMetrics.widthPixels
    val heightPx = displayMetrics.heightPixels
    val density = displayMetrics.density
    val dpi = displayMetrics.densityDpi

    val screenInches = sqrt((heightPx.toDouble().pow(2.0) +
            widthPx.toDouble().pow(2.0))).div(dpi.toDouble())
        .toBigDecimal().setScale(1, RoundingMode.HALF_UP).toFloat()

    fun calGCD(height: Int, width: Int): Int {
        if (width == 0)
            return height;
        return calGCD(width, height % width);
    }
    val gcd = calGCD(heightPx, widthPx)

    val pxStr = "Width : $widthPx px, $widthDP dp"
    val dpStr = "Height : $heightPx px, $heightDP dp"
    val densityStr = "Density: $density, dpi: $dpi"
    val screenSize = "Screen size: $screenInches\" inch"
    val aspectRatioStr = "Aspect Ratio - " + heightPx/gcd + ":" + widthPx/gcd

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(end = 30.dp)
                .align(Alignment.End),
            text = pxStr+"\n"+dpStr+"\n"+densityStr+"\n"+aspectRatioStr+"\n"+screenSize,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            lineHeight = 15.sp
        )
    }
}