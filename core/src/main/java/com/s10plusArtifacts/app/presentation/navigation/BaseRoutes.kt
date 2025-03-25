package com.s10plusArtifacts.app.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class BaseRoutes {


    @Serializable
    data class SplashScreen(val token:String = ""): BaseRoutes()

    @Serializable
    data object MainScreen: BaseRoutes()

    @Serializable
    data class ChangeScreen(val id:String = ""): BaseRoutes()

    @Serializable
    data object  DataUser: BaseRoutes()
}