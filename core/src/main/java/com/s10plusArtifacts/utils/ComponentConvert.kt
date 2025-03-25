package com.s10plusArtifacts.utils

import androidx.compose.runtime.Composable

data class ComponentConvert(
    var properties: ComponentProperties,
    val actions: Actions?,
    var component: @Composable (onclick: (Actions,DataAnalitic) -> Unit) -> Unit,
)
