package com.s10plusArtifacts.componets.bases

abstract class BaseRemoteDataSource {
    protected suspend fun <Out> getResult(
        call:suspend () -> Out
    ): Result<Out> = try {
        Result.success(call())
    } catch (e:Exception) {
        Result.failure(e)
    }
}