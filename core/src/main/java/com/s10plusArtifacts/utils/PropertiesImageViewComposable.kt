package com.s10plusArtifacts.utils

data class PropertiesImageViewComposable(
    var url:String? = "https://picsum.photos/200/30",
    val paddingTop:Int = 0,
    val paddingBotton:Int = 0,
    val paddingStart: Int = 0,
    val paddingEnd: Int = 0,
    val size:String = "50 50",
    var heigth: Float = 0f,
    var width: Float = 0f,
    var posX:Int = 0,
    var posY:Int = 0,
    val backgroundView: String = "#ffffff",
    var actions:Actions? = null
)
