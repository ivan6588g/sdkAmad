package com.s10plusArtifacts.app.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.s10plusArtifacts.app.presentation.screens.ChangeView
import com.s10plusArtifacts.app.presentation.screens.DataUserScreen
import com.s10plusArtifacts.app.presentation.screens.MainView
import com.s10plusArtifacts.app.presentation.screens.SplashScreen

fun NavGraphBuilder.mainGraph(navAction: NavigationBase){
    composable<BaseRoutes.SplashScreen>() {
        blackStackEntry ->
        val parameter = blackStackEntry.toRoute<BaseRoutes.SplashScreen>()
        SplashScreen(token = parameter.token) {
            navAction.navigateTo(it)
        }
    }

    composable<BaseRoutes.MainScreen> {
        MainView(goToChangeView = {
            navAction.navigateTo(BaseRoutes.ChangeScreen(it))
        })
    }

    composable<BaseRoutes.ChangeScreen>() {
        blackStackEntry ->
        val paraameters = blackStackEntry.toRoute<BaseRoutes.ChangeScreen>()
        ChangeView(id = paraameters.id)
    }
    composable<BaseRoutes.DataUser>() {
        backStackEntry ->
        DataUserScreen(){
            screen ->
            navAction.navigateTo(screen)
        }
    }
}