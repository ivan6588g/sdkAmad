package com.s10plusArtifacts.app.domain.repository.remote

import com.s10plusArtifacts.app.domain.model.RequestClick
import com.s10plusArtifacts.app.domain.entities.TokenRequestModel
import com.s10plusArtifacts.app.domain.model.ResponceMunicipiosSepoMex
import com.s10plusArtifacts.app.domain.model.ResponseColoniaSepoMex
import com.s10plusArtifacts.app.domain.model.ResponseStatesSepoMex
import com.s10plusArtifacts.app.domain.services.BaseService
import com.s10plusArtifacts.app.domain.services.BaseServiceSepoMex
import com.s10plusArtifacts.componets.bases.BaseRemoteDataSource
import com.s10plusArtifacts.network.BaseModel
import com.s10plusArtifacts.utils.Configuration
import com.s10plusArtifacts.utils.DynamicServiceManager
import javax.inject.Inject

class BaseRemoteDataSourceImp @Inject constructor(
    private val baseServiceSepo: BaseServiceSepoMex
): BasePrincipalRemoteDataSource,BaseRemoteDataSource() {
    private val baseService: BaseService
        get() = DynamicServiceManager.createService(BaseService::class.java)

    override suspend fun getConfig(
        id: String
    ): Result<BaseModel<Configuration>> =
        getResult { baseService.getConfig(id) }


    override suspend fun load(token: TokenRequestModel): Result<BaseModel<Nothing>> =
        getResult { baseService.load(token) }

    override suspend fun getStatesSepoMex(): Result<List<ResponseStatesSepoMex>> =
        getResult { baseServiceSepo.getAllState() }

    override suspend fun getMunicipio(id: String): Result<List<ResponceMunicipiosSepoMex>> =
        getResult { baseServiceSepo.getMunicipioXEstado(id = id) }

    override suspend fun getColonia(id: String): Result<List<ResponseColoniaSepoMex>> =
        getResult { baseServiceSepo.getColoniasXMunicipios(id = id) }

    override suspend fun click(request: RequestClick): Result<BaseModel<Any>> =
        getResult { baseService.sendClick(request.token,request.rq) }


}