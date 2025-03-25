package com.s10plusArtifacts.app.mapper

import com.s10plusArtifacts.bases.Mapper
import com.s10plusArtifacts.network.BaseModel
import com.s10plusArtifacts.utils.Configuration
import javax.inject.Inject

class GetConfigurationMapper @Inject constructor():
    Mapper<BaseModel<Configuration>, Configuration>() {
    override fun map(info: BaseModel<Configuration>): Configuration {
        return info.data
    }
}