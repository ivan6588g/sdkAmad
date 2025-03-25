package com.s10plusArtifacts.componets.Dialogs

import com.s10plusArtifacts.utils.Constants

data class DialogModel(
    val title:String = Constants.EMPTY_STRING,
    val subTitle:String = Constants.EMPTY_STRING,
    val txtButton1:String = Constants.EMPTY_STRING,
    val txtButton2:String = Constants.EMPTY_STRING
)
