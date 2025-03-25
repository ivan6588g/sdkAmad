package com.s10plusArtifacts.utils

import com.s10plusArtifacts.AppBuildConfig
import com.s10plusArtifacts.network.NetworkHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DynamicServiceManager {

    private var baseUrl: String = AppBuildConfig.BASE_URL

    fun updateBaseUrl(newUrl: String) {
        baseUrl = newUrl
    }

    fun activateQa(){
       updateBaseUrl("https://s10plus.com:8443/wsqa/rest/action/")
    }
    fun activateDebug(){
        updateBaseUrl("https://s10plus.com:8443/wsdemos/rest/action/")
    }

    private fun createRetrofit(): Retrofit {
        val okHttpClient = NetworkHelper.getOkHttpBuilder().build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return createRetrofit().create(serviceClass)
    }
}
