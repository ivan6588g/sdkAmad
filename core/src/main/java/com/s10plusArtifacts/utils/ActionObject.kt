package com.s10plusArtifacts.utils

data class ActionObject(
    var acction: ActionEnum =ActionEnum.NOTHING,
    var value : List<String> = arrayListOf()
)
