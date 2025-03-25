package com.s10plusArtifacts.app.mapper

import com.s10plusArtifacts.bases.Mapper
import com.s10plusArtifacts.network.BaseModel
import javax.inject.Inject

class ClickMapper @Inject constructor():
Mapper<BaseModel<Any>,BaseModel<Any>>(){
    override fun map(info: BaseModel<Any>): BaseModel<Any> {
        return  info
    }
}