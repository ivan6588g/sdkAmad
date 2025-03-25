package com.s10plusArtifacts.utils

import androidx.compose.ui.text.style.TextAlign


fun getAligmentText(aligment:String): TextAlign {
    when(aligment){
        "CENTER" -> return TextAlign.Center
        "LEFT" -> return TextAlign.Left
        "END" -> return TextAlign.End
        "RIGHT" -> return TextAlign.Right
        "START" ->  return TextAlign.Start
        else -> return TextAlign.Center
    }
}

