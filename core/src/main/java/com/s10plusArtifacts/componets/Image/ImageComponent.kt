package com.s10plusArtifacts.componets.Image

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.s10plusArtifacts.utils.Actions
import com.s10plusArtifacts.utils.ComponentProperties
import com.s10plusArtifacts.utils.DataAnalitic
import com.s10plusArtifacts.utils.ScalingUtils


@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ImageComponent(propertiesImageViewComposable: ComponentProperties,actions: Actions?, onClick : (Actions,DataAnalitic) -> Unit) {
    val internetPermissionState = rememberPermissionState(Manifest.permission.INTERNET)
    val (scaledWidth, scaledHeight) = ScalingUtils.scaleDimensionsToDp(
        width = propertiesImageViewComposable.size.width, // Ancho original
        height = propertiesImageViewComposable.size.height, // Alto original
        serviceWidth = 400f, // Ancho de referencia
        serviceHeight = 800f // Alto de referencia
    )

    if (internetPermissionState.status.isGranted) {
        Box{
            GlideImage(
                modifier = Modifier.clickable {
                    actions.let {
                        if (it != null) {
                            onClick.invoke(it,DataAnalitic(id = propertiesImageViewComposable.idAnalytics ?: "", action = propertiesImageViewComposable.actionAnalytics ?: ""))
                        }
                    }
                }.height(scaledHeight).
                width(scaledWidth),
                model = propertiesImageViewComposable.base64Image,
                contentScale = ContentScale.FillBounds,
                contentDescription = ""

            ) { image ->
                image.diskCacheStrategy(DiskCacheStrategy.ALL)
            }
        }


    } else {
        internetPermissionState.launchPermissionRequest()
    }

}




