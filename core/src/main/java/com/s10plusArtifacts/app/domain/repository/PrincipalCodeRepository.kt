package com.s10plusArtifacts.app.domain.repository

import com.s10plusArtifacts.app.domain.entities.TokenRequestModel
import com.s10plusArtifacts.app.domain.model.RequestClick
import com.s10plusArtifacts.app.domain.model.ResponceMunicipiosSepoMex
import com.s10plusArtifacts.app.domain.model.ResponseColoniaSepoMex
import com.s10plusArtifacts.app.domain.model.ResponseStatesSepoMex
import com.s10plusArtifacts.app.domain.repository.remote.BasePrincipalRemoteDataSource
import com.s10plusArtifacts.componets.bases.BaseRemoteDataSource
import com.s10plusArtifacts.network.BaseModel
import com.s10plusArtifacts.utils.Configuration
import javax.inject.Inject

class PrincipalCodeRepository @Inject constructor(
    private val remoteBasePrincipalRemoteDataSource: BasePrincipalRemoteDataSource
): PrincipalRepositoryDataSource,BaseRemoteDataSource() {
    override suspend fun getConfiguration(id: String): Result<BaseModel<Configuration>> =
        remoteBasePrincipalRemoteDataSource.getConfig(id)

    override suspend fun load(token: TokenRequestModel): Result<BaseModel<Nothing>> =
        remoteBasePrincipalRemoteDataSource.load(token)

    override suspend fun getStateSepo(): Result<List<ResponseStatesSepoMex>> =
        remoteBasePrincipalRemoteDataSource.getStatesSepoMex()

    override suspend fun getMunicipioSepo(id: String): Result<List<ResponceMunicipiosSepoMex>> =
        remoteBasePrincipalRemoteDataSource.getMunicipio(id = id)

    override suspend fun getColoniasSepo(id: String): Result<List<ResponseColoniaSepoMex>> =
        remoteBasePrincipalRemoteDataSource.getColonia(id = id)

    override suspend fun click(request: RequestClick): Result<BaseModel<Any>> =
        remoteBasePrincipalRemoteDataSource.click(request)

}