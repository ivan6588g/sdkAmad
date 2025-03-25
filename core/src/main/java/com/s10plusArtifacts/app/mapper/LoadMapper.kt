package com.s10plusArtifacts.app.mapper

import com.s10plusArtifacts.bases.Mapper
import com.s10plusArtifacts.network.BaseModel
import javax.inject.Inject

class LoadMapper @Inject constructor(): Mapper<BaseModel<Nothing>,String>() {
    override fun map(info: BaseModel<Nothing>): String {
        return info.token ?: ""
    }
}