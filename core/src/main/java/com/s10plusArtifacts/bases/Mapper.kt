package com.s10plusArtifacts.bases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


abstract class Mapper<In,Out> {
    fun domainToPresentation(info: Flow<Result<In>>) : Flow<Result<Out>> = info.map {
         domainToPresentation(it)
    }


    fun domainToPresentation(info: Result<In>): Result<Out> =
        when{
            info.isSuccess -> Result.success(map((info.getOrNull()!!)))
            else -> Result.failure(info.exceptionOrNull()!!)
        }
    abstract fun map(info:In):Out
}