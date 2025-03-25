package com.s10plusArtifacts.app.presentation.navigation

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun InitAmadCore(modifier: Modifier = Modifier,token:String,backView:Boolean){
   val context = LocalContext.current
    val navController:NavHostController=  rememberNavController()
    val navActions: NavigationBase = remember(navController) {
        NavigationBase(navController)
    }
    NavHost(
        navController = navController,
        startDestination = BaseRoutes.SplashScreen(token = token),//BaseRoutes.SplashScreen(token = token),BaseRoutes.DataUser
        modifier = modifier
    ){
        mainGraph(navActions)
    }

        BackHandler {
            if(backView) {
            context.findActivity()?.finish()
            }
        }

}


fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}