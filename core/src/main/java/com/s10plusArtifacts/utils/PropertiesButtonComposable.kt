package com.s10plusArtifacts.utils

data class PropertiesButtonComposable(
    var backgroundColor:String = "#ffffff",
    var colorText:String = "#000000",
    var heigh:Float = 30f,
    var whith:Float = 30f,
    var positionX:Float = 0f,
    var positionY:Float = 0f,
    var rounded:Int = 0,
    val type:String = "Text",
    var textButton:String = "Defualt",
    var actions:Actions? = null,
    val icon:String? = "",
    val iconColor:String? = "",
    var alingText:String = "MC",
)
