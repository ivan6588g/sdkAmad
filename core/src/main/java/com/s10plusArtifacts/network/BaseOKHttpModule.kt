package com.s10plusArtifacts.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseOKHttpModule {

    @Singleton
    @Provides
    @WSAmadR
    fun provideAmadService(@ApplicationContext context: Context): OkHttpClient =
        NetworkHelper.getOkHttpBuilder()
            .build()


}