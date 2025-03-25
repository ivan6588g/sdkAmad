package com.s10plusArtifacts.app.di

import android.content.Context
import com.s10plusArtifacts.app.domain.services.BaseService
import com.s10plusArtifacts.app.domain.services.BaseServiceSepoMex
import com.s10plusArtifacts.network.AmadSepoMex
import com.s10plusArtifacts.network.AmadUrl
import com.s10plusArtifacts.network.BaseOKHttpModule
import com.s10plusArtifacts.network.NetworkHelper
import com.s10plusArtifacts.network.WSAmadR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton
@Module(includes = [BaseOKHttpModule::class])
@InstallIn(SingletonComponent::class)
class BaseModule {

    @Singleton
    @Provides
    fun providesBase(
        @AmadUrl url:String,
        @WSAmadR okHttpClient: OkHttpClient
    ) = NetworkHelper.provideService<BaseService>(okHttpClient,url)

    @Singleton
    @Provides
    fun provideBaseSepoMex(
        @AmadSepoMex url :String,
        @WSAmadR okHttpClient: OkHttpClient
    ) = NetworkHelper.provideService<BaseServiceSepoMex>(okHttpClient,url)

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext:Context):Context{return appContext}
}