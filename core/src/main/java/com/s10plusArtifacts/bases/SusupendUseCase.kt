package com.s10plusArtifacts.bases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in params,out Results,Type>(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val mapper: Mapper<Type,Results>
) {

    suspend operator fun invoke(params: params): Result<Results>{
        return try {
            withContext(coroutineDispatcher){
                mapper.domainToPresentation(execute(params))
            }
        } catch (e:Exception){
            Result.failure(e)
        }
    }

    protected abstract suspend fun execute(params: params): Result<Type>
}

suspend operator fun <Results,Type> SuspendUseCase<Unit,Results,Type>.invoke(): Result<Results> =
    this(Unit)