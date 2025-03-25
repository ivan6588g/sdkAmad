package com.s10plusArtifacts.extensions

import com.s10plusArtifacts.exceptions.ExceptionBaseResponse
import com.s10plusArtifacts.network.Errors
import retrofit2.HttpException

fun <T> Result<T>.onResult(
    onSuccess: (data: T?) -> Unit,
    onFailure: (ExceptionBaseResponse?) -> Unit
) =
    when {
        isSuccess -> onSuccess(this.getOrNull())
        isFailure -> onFailure(this.exceptionOrNull()?.let {
            if (it is HttpException) {
                it.toExceptionBase()
            } else {
                it as ExceptionBaseResponse

            }
        })

        else -> onFailure(this.exceptionOrNull()?.let { ExceptionBaseResponse(it) })
    }

fun HttpException.toExceptionBase(): ExceptionBaseResponse {
    return when (this.code()) {
        404 -> ExceptionBaseResponse(Errors("404", "Usuario no autentucado", "Atencion"))
        else -> {
            ExceptionBaseResponse(Errors("", "Error Desconocido", "Atención"))
        }
    }
}