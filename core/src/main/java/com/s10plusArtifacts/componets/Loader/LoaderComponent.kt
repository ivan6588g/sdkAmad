package com.s10plusArtifacts.componets.Loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Loader(isLoading: Boolean, modifier: Modifier = Modifier) {
    if (isLoading) {
        Box(
            modifier = modifier
                .background(Color.Black.copy(alpha = 0.5f)), // Fondo semitransparente
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White) // Loader principal
        }
    }
}