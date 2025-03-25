package com.s10plusArtifacts.app.usecase

import com.s10plusArtifacts.app.domain.model.ResponseStatesSepoMex
import com.s10plusArtifacts.app.domain.repository.PrincipalRepositoryDataSource
import com.s10plusArtifacts.app.mapper.GetAllStateSepo
import com.s10plusArtifacts.bases.SuspendUseCase
import com.s10plusArtifacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllStateSepo @Inject constructor(
    private val principalRepositoryDataSource: PrincipalRepositoryDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    mapper: GetAllStateSepo
    ): SuspendUseCase<String,List<ResponseStatesSepoMex>,List<ResponseStatesSepoMex>>(dispatcher,mapper) {
    override suspend fun execute(params: String): Result<List<ResponseStatesSepoMex>> =
        principalRepositoryDataSource.getStateSepo()
}