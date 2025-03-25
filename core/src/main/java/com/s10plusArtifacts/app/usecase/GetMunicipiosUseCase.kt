package com.s10plusArtifacts.app.usecase

import com.s10plusArtifacts.app.domain.model.ResponceMunicipiosSepoMex
import com.s10plusArtifacts.app.domain.repository.PrincipalRepositoryDataSource
import com.s10plusArtifacts.app.mapper.getMunicipiosMapper
import com.s10plusArtifacts.bases.SuspendUseCase
import com.s10plusArtifacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMunicipiosUseCase @Inject constructor(
    private val principalRepositoryDataSource: PrincipalRepositoryDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    mapper: getMunicipiosMapper
): SuspendUseCase<String,List<ResponceMunicipiosSepoMex>,List<ResponceMunicipiosSepoMex>>(dispatcher,mapper) {
    override suspend fun execute(params: String): Result<List<ResponceMunicipiosSepoMex>> =
        principalRepositoryDataSource.getMunicipioSepo(params)
}