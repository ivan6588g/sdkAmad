package com.s10plusArtifacts.componets.Box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.s10plusArtifacts.utils.getHeight
import com.s10plusArtifacts.utils.getWidth

@Composable
fun BoxBase(background:Color,height:Float = 0f,width:Float = 0f,isScroll:Boolean = false,content: @Composable () -> Unit){

    Box(modifier = Modifier
        .background(background)
        .getWidth(width)
        .getHeight(height)
        .verticalScroll(rememberScrollState())
    ){
        content.invoke()
    }
}