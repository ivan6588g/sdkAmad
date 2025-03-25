package com.s10plusArtifacts.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder

class NavigationBase(private val navController: NavController) {

    fun navigateTo(baseRoutes: BaseRoutes){
        when(baseRoutes){
            is BaseRoutes.SplashScreen -> {
                this.navigateToSplash(baseRoutes.token)
            }

            is BaseRoutes.MainScreen -> {
                this.navigateToMain()
            }
            is BaseRoutes.ChangeScreen -> {
                this.navigateToChangeView(baseRoutes.id)
            }

            is BaseRoutes.DataUser -> {
                this.navigateToDataUser()
            }
        }
    }

    fun popBackStack(){
        navController.popBackStack()
    }

    private  fun navigateToDataUser(){
            navController.navigate(BaseRoutes.DataUser){
                popUpTo(navController.graph.startDestinationId) { inclusive = true } // Limpia el stack correctamente
                launchSingleTop = true
            }

        //navController.navigate(BaseRoutes.DataUser(info = personalInfo))
    }
    private fun navigateToSplash(token:String){
        navController.navigate(BaseRoutes.SplashScreen(token)){
            popUpTo(navController.graph.startDestinationId) { inclusive = true } // Limpia el stack correctamente
            launchSingleTop = true
        }
    }

    private fun navigateToMain(){
        navController.navigate(BaseRoutes.MainScreen){

        }
    }

    private fun navigateToChangeView(id:String){
        navController.navigate(BaseRoutes.ChangeScreen(id = id))
    }


    private fun cleanStack(it:NavOptionsBuilder){
        it.popUpTo(navController.graph.findStartDestination().id){saveState = true}
        it.launchSingleTop = true
        it.restoreState = true
    }
}