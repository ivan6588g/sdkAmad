package com.s10plusArtifacts.app.domain.repository

import com.s10plusArtifacts.app.domain.entities.TokenRequestModel
import com.s10plusArtifacts.app.domain.model.RequestClick
import com.s10plusArtifacts.app.domain.model.ResponceMunicipiosSepoMex
import com.s10plusArtifacts.app.domain.model.ResponseColoniaSepoMex
import com.s10plusArtifacts.app.domain.model.ResponseStatesSepoMex
import com.s10plusArtifacts.network.BaseModel
import com.s10plusArtifacts.utils.Configuration

interface PrincipalRepositoryDataSource {

    suspend fun getConfiguration(id:String):Result<BaseModel<Configuration>>
    suspend fun load(token: TokenRequestModel):Result<BaseModel<Nothing>>
    suspend fun getStateSepo():Result<List<ResponseStatesSepoMex>>
    suspend fun getMunicipioSepo(id:String):Result<List<ResponceMunicipiosSepoMex>>
    suspend fun getColoniasSepo(id:String):Result<List<ResponseColoniaSepoMex>>
    suspend fun click(request: RequestClick):Result<BaseModel<Any>>

}