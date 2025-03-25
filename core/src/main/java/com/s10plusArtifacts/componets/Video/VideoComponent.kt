package com.s10plusArtifacts.componets.Video

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.s10plusArtifacts.utils.Actions
import com.s10plusArtifacts.utils.AligmentEnum
import com.s10plusArtifacts.utils.ComponentProperties
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.HexToJetpackColor
import com.s10plusArtifacts.utils.ScalingUtils
import com.s10plusArtifacts.utils.getAligment

@Composable
fun VideoComponent(
    properties: ComponentProperties,action: Actions?, autoPlay: Boolean = false,
    repeat: Boolean = true, onClick: (Actions, DataAnalitic) -> Unit
) {

    val context = LocalContext.current

    val (scaledWidth, scaledHeight) = ScalingUtils.scaleDimensionsToDp(
        width = properties.size.width, // Ancho original
        height = properties.size.height, // Alto original
        serviceWidth = 400f, // Ancho de referencia
        serviceHeight = 800f // Alto de referencia
    )
    if (properties.videoURL.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .height(scaledWidth)
                .width(scaledHeight),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Video no disponible", color = Color.Gray)
        }
        return
    }
    // Crear una instancia de ExoPlayer de Media3
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(properties.videoURL))
                .build()
            setMediaItem(mediaItem)
            playWhenReady = autoPlay
            repeatMode = if (repeat) ExoPlayer.REPEAT_MODE_ALL else ExoPlayer.REPEAT_MODE_OFF
            prepare()
        }
    }


    Box(
        modifier = Modifier
            .height(scaledWidth)
            .width(scaledHeight)
            .background(color =
            if(properties.background != null){
                HexToJetpackColor.getColor(properties.background)
            }else{
                Color.White
            }
            ),
        contentAlignment = getAligment(
            (properties.textAlignment ?: AligmentEnum.MEDIUMCENTER).toString()
        )
    )
    {
        // AndroidView para mostrar el PlayerView de Media3
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = true // Muestra los controles del reproductor

                }
            },
            modifier = Modifier.height(scaledWidth)
                .width(scaledHeight)
        )
    }


    // Liberar recursos al desmontar el Composable
    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

}