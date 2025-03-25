package com.s10plusArtifacts.utils

data class PropertiesTextViewComposable(
    val paddingTop:Int = 0,
    val paddingBotton:Int = 0,
    val paddingStart: Int = 0,
    val paddingEnd: Int = 0,
    val textSizes: Int = 10,
    var sizeHeight: Float = 30f,
    var sizeWidth: Float = 30f,
    var posX:Int = 0,
    var posY:Int = 0,
    var text: String? = "DEFAULT",
    val alignmentText: String = "CENTER",
    val backgroundColor:String ="#FFFFFF",
    var textColor:String = "#000000"
)