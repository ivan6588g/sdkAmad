package com.s10plusArtifacts.sesion

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModuleCore {

    @Binds
    abstract fun sessionBind(sessionDelegateImpl: SessionDelegateCoreImpl):SessionDelegateCore
}