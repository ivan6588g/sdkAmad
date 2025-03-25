package com.s10plusArtifacts.app.di

import com.s10plusArtifacts.app.domain.repository.PrincipalCodeRepository
import com.s10plusArtifacts.app.domain.repository.PrincipalRepositoryDataSource
import com.s10plusArtifacts.app.domain.repository.remote.BasePrincipalRemoteDataSource
import com.s10plusArtifacts.app.domain.repository.remote.BaseRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseDataSourceModule {

    @Binds
    abstract fun bindBasePrincipalRemote(repository: BaseRemoteDataSourceImp): BasePrincipalRemoteDataSource

    @Binds
    abstract fun bindBasePrincipalRepository(repository: PrincipalCodeRepository): PrincipalRepositoryDataSource
}