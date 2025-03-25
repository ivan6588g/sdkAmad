package com.s10plusArtifacts.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.DisplayMetrics
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.s10plusArtifacts.componets.Buttons.ButtonImage
import com.s10plusArtifacts.componets.Buttons.ButtonNs
import com.s10plusArtifacts.componets.Carousel.CarouselComponent
import com.s10plusArtifacts.componets.Image.ImageComponent
import com.s10plusArtifacts.componets.TextView.TextViewComponent
import com.s10plusArtifacts.componets.TimerVisibility.TimerVisibility
import com.s10plusArtifacts.componets.Video.VideoComponent
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val Int.PxtoDp: Float
    get() = (this / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
val Int.DptoPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


@Composable
fun Modifier.getHeight(height: Float): Modifier {
    if (height < 0) {
        return this.fillMaxHeight()
    } else if (height > 0) {
        return this.height(with(LocalDensity.current) { height.toDp() })
    } else {
        return this
    }
}

@Composable
fun Modifier.getWidth(width: Float): Modifier {
    if (width < 0) {
        return this.fillMaxWidth()
    } else if (width > 0) {
        return this.width(with(LocalDensity.current) { width.toDp() })
    } else {
        return this
    }
}

@Composable
fun Modifier.getScroll(isScroll: Boolean): Modifier {
    return if (isScroll) {
        this.verticalScroll(rememberScrollState())
    } else {
        this
    }
}

@Composable
fun Modifier.getOffsetX(posX: Int): Modifier {
    if (posX < 0) {
        return this
    } else {
        return this.offset(x = posX.dp)
    }
}

fun getAligment(alig: String): Alignment {
    return when (alig) {
        AligmentEnum.TOPSTART.alig -> {
            Alignment.TopStart
        }

        AligmentEnum.TOPCENTER.alig -> {
            Alignment.TopCenter
        }

        AligmentEnum.TOPEND.alig -> {
            Alignment.TopEnd
        }

        AligmentEnum.MEDIUMSTART.alig -> {
            Alignment.CenterStart
        }

        AligmentEnum.MEDIUMCENTER.alig -> {
            Alignment.Center
        }

        AligmentEnum.MEDIUMEND.alig -> {
            Alignment.CenterEnd
        }

        AligmentEnum.ENDSTART.alig -> {
            Alignment.BottomStart
        }

        AligmentEnum.ENDEND.alig -> {
            Alignment.BottomCenter
        }

        AligmentEnum.ENDEND.alig -> {
            Alignment.BottomEnd
        }

        else -> {
            Alignment.Center
        }
    }
}

@Composable
fun Modifier.getOffsetY(posY: Int): Modifier {
    if (posY < 0) {
        return this
    } else {
        return this.offset(y = posY.dp)
    }
}

fun getColorFromString(color: String): Color {
    return Color(android.graphics.Color.parseColor(color))
}

object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }
}

fun getScreenDimensions(context: Context): Pair<Int, Int> {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels // Ancho en píxeles
    val screenHeight = displayMetrics.heightPixels // Alto en píxeles
    return Pair(screenWidth, screenHeight)
}

fun convertImageView(properties: ComponentProperties, actions: Actions?): ComponentConvert {
    val objComponent = ComponentConvert(
        component =
        { onClick ->
            TimerVisibility(
                targetTime = 3,
                if (actions?.showBySchedule?.isNotEmpty() == true) actions.showBySchedule[0] else null
            ) {
                ImageComponent(
                    propertiesImageViewComposable = properties,
                    actions = actions
                ) { action, data ->
                    onClick.invoke(action, data)
                }
            }

        }, properties = properties, actions = actions
    )
    return objComponent
}

fun convertTextView(properties: ComponentProperties, actions: Actions?): ComponentConvert {

    val objectComponent = ComponentConvert(
        component =
        { onClick ->
            TimerVisibility(
                targetTime = 3,
                if (actions?.showBySchedule?.isNotEmpty() == true) actions.showBySchedule[0] else null
            ) {
                TextViewComponent(
                    propertiesTextViewComposable = properties,
                    actions
                ) { actions, data ->
                    onClick.invoke(actions,data)

                }
            }

        }, properties = properties, actions = actions
    )
    return objectComponent
}

fun convertCorouselView(properties: ComponentProperties, actions: Actions): ComponentConvert {
    val objectComponent = ComponentConvert(
        component = {
            TimerVisibility(
                targetTime = 3,
                if (actions.showBySchedule.isNotEmpty()) actions.showBySchedule[0] else null
            ) {
                CarouselComponent(propertiesCarrouselViewComposable = properties)
            }
        },
        properties = properties,
        actions = actions
    )
    return objectComponent
}

fun convertButtonView(properties: ComponentProperties, actions: Actions?): ComponentConvert {

    val componentButton = ComponentConvert(component = { onClick ->
        TimerVisibility(
            targetTime = 3,
            if (actions?.showBySchedule?.isNotEmpty() == true) actions.showBySchedule[0] else null
        ) {
            ButtonNs(propertiesButtonComposable = properties, actions = actions) { actions, data ->
                onClick.invoke(actions, data)
            }

        }
    }, actions = actions, properties = properties)

    return componentButton
}

fun convertButtonImage(properties: ComponentProperties, actions: Actions?): ComponentConvert {
    return ComponentConvert(component = { onclick ->
        TimerVisibility(
            targetTime = 3,
            if (actions?.showBySchedule?.isNotEmpty() == true) actions.showBySchedule[0] else null
        ) {
            ButtonImage(properties = properties, action = actions) { action, data ->
                onclick.invoke(action, data)
            }
        }
    }, actions = actions, properties = properties)
}

fun convertVideoView(properties: ComponentProperties,actions: Actions?):ComponentConvert {
    return  ComponentConvert(component = { onclick ->
        TimerVisibility(
            targetTime = 3,
            if (actions?.showBySchedule?.isNotEmpty() == true) actions.showBySchedule[0] else null
        ) {
            VideoComponent(properties = properties, action = actions) { action, data ->
                onclick.invoke(action, data)
            }
        }
    }, actions = actions, properties = properties)
}

@SuppressLint("SuspiciousIndentation")
fun convertActionIntent(actions: Actions): Intent? {
    val actionActive: ActionObject = ActionObject()
    if (actions.call.isNotEmpty()) {
        actionActive.acction = ActionEnum.CALL
        actionActive.value = arrayListOf(actions.call)
    } else if (actions.openWebView.isNotEmpty()) {
        actionActive.acction = ActionEnum.OPENWEB
        actionActive.value = arrayListOf(actions.openWebView)
    } else if (actions.openSections.isNotEmpty()) {
        actionActive.acction = ActionEnum.OPENSECCION
        actionActive.value = arrayListOf(actions.openSections)
    }
    when (actionActive.acction) {
        ActionEnum.CALL -> {
            return Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${actionActive.value[Constants.ZERO]}")
            )

        }

        ActionEnum.OPENWEB -> {
            val formattedUrl =
                if (actionActive.value[Constants.ZERO].startsWith("http://") || actionActive.value[Constants.ZERO].startsWith(
                        "https://"
                    )
                ) {
                    actionActive.value[Constants.ZERO] // La URL ya tiene un esquema válido
                } else {
                    "http://${actionActive.value[Constants.ZERO]}" // Agrega "http://" como esquema predeterminado
                }
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(formattedUrl)
            }
            return intent
        }

        ActionEnum.OPENSECCION -> {
            return Intent(Intent.ACTION_VIEW, Uri.parse(actionActive.value[Constants.ZERO]))
        }

        ActionEnum.SHOWSCHEDULE -> {
            return Intent(Intent.ACTION_VIEW, Uri.parse(actionActive.value[Constants.ZERO]))
        }

        ActionEnum.NOTHING -> {
            return null
        }
    }
}

fun parseTime(time: String, formatter: DateTimeFormatter): LocalTime? {
    return try {
        LocalTime.parse(time.trim().uppercase(), formatter).withSecond(0).withNano(0)
    } catch (e: Exception) {
        println("Error al analizar la hora: ${e.message}")
        null
    }
}


