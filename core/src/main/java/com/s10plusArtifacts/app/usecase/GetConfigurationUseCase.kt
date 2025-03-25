package com.s10plusArtifacts.app.usecase

import com.s10plusArtifacts.app.domain.repository.PrincipalRepositoryDataSource
import com.s10plusArtifacts.app.mapper.GetConfigurationMapper
import com.s10plusArtifacts.bases.SuspendUseCase
import com.s10plusArtifacts.di.IoDispatcher
import com.s10plusArtifacts.network.BaseModel
import com.s10plusArtifacts.utils.Configuration
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val principalRepositoryDataSource: PrincipalRepositoryDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    mapper: GetConfigurationMapper
):SuspendUseCase<String,Configuration,BaseModel<Configuration>>(dispatcher,mapper){
    override suspend fun execute(params: String): Result<BaseModel<Configuration>> =
        principalRepositoryDataSource.getConfiguration(params)
}