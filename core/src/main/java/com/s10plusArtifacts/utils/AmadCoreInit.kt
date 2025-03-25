package com.s10plusArtifacts.utils

import android.content.Context
import com.s10plusArtifacts.AppNavigationModule
import com.s10plusArtifacts.coreSdk.BuildConfig


fun AmadCoreInit(context: Context, token: String,backView:Boolean,variant:String = ""){
    when(variant){
        "qa" -> {DynamicServiceManager.activateQa()}
        "debug" -> {DynamicServiceManager.activateDebug()}
        else -> {DynamicServiceManager.updateBaseUrl(BuildConfig.BASE_URL)}
    }
    AppNavigationModule.OpenMain(context, token = token, backView = backView)
}