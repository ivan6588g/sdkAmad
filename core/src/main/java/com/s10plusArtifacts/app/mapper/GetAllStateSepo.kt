package com.s10plusArtifacts.app.mapper

import com.s10plusArtifacts.app.domain.model.ResponseStatesSepoMex
import com.s10plusArtifacts.bases.Mapper
import javax.inject.Inject

class GetAllStateSepo @Inject constructor():
Mapper<List<ResponseStatesSepoMex>,List<ResponseStatesSepoMex>>(){
    override fun map(info: List<ResponseStatesSepoMex>): List<ResponseStatesSepoMex> {
        return info
    }

}