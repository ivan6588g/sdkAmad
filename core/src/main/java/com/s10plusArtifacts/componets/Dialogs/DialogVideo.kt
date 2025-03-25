package com.s10plusArtifacts.componets.Dialogs

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.s10plusArtifacts.componets.bases.DialogBase

@Composable
fun DialogVideo(
    urlVideo:String,
    autoPlay: Boolean = true,
    repeat: Boolean = true,
    shouldShowDialog:Boolean,
    onDismissRequest: ()->Unit,
){
    val context = LocalContext.current
    // Crear una instancia de ExoPlayer de Media3
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(urlVideo))
                .build()
            setMediaItem(mediaItem)
            playWhenReady = autoPlay
            repeatMode = if (repeat) ExoPlayer.REPEAT_MODE_ALL else ExoPlayer.REPEAT_MODE_OFF
            prepare()
        }
    }
    // Liberar recursos al desmontar el Composable
    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }
    DialogBase(
        onDismissRequest = { onDismissRequest.invoke()},
        shouldShowDialog = shouldShowDialog,
        title = "" ,
        subTitle =  "",
        icon = null,
        showCloseIcon = true,
        content = {
            AndroidView(
                factory = {
                    PlayerView(it).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier.fillMaxHeight(0.3f)
            )

        }

    )
}