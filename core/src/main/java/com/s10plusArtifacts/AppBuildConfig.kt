package com.s10plusArtifacts

import com.s10plusArtifacts.utils.Constants
import com.s10plusArtifacts.coreSdk.BuildConfig
import com.s10plusArtifacts.coreSdk.BuildConfig.BUILD_TYPE

object AppBuildConfig {
    const val BASE_URL= BuildConfig.BASE_URL
    const val BASE_URL_SEPOMEX = BuildConfig.BASE_URL_SEPOMEX

    fun isReleaseDebug() = BuildConfig.DEBUG && (BUILD_TYPE == Constants.BUILD_QA)
}