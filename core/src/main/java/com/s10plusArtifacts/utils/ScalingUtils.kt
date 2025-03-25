package com.s10plusArtifacts.utils
import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ScalingUtils {

    /**
     * Obtiene las dimensiones de la pantalla en píxeles.
     */
    fun getScreenDimensions(context: Context): Pair<Int, Int> {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        return Pair(screenWidth, screenHeight)
    }

    /**
     * Escala las coordenadas (x, y) y las convierte directamente a dp.
     */
    @Composable
    fun scaleCoordinatesToDp(
        x: Float,
        y: Float,
        serviceWidth: Float = 400f,
        serviceHeight: Float = 800f
    ): Pair<Dp, Dp> {
        val density = LocalDensity.current
        val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
        val screenHeightPx = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

        val scaledX = x / serviceWidth * screenWidthPx
        val scaledY = y / serviceHeight * screenHeightPx

        return Pair(with(density) { scaledX.toDp() }, with(density) { scaledY.toDp() })
    }

    /**
     * Escala las dimensiones (width, height) y las convierte directamente a dp.
     */
    @Composable
    fun scaleDimensionsToDp(
        width: Float,
        height: Float,
        serviceWidth: Float = 400f,
        serviceHeight: Float = 800f
    ): Pair<Dp, Dp> {
        val density = LocalDensity.current
        val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
        val screenHeightPx = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

        val scaledWidth = width / serviceWidth * screenWidthPx
        val scaledHeight = height / serviceHeight * screenHeightPx

        return Pair(with(density) { scaledWidth.toDp() }, with(density) { scaledHeight.toDp() })
    }
}
