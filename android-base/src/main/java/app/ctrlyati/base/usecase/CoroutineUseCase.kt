package com.finema.kkudigitalid.common.base.usecase

import com.finema.kkudigitalid.common.base.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class CoroutineUseCase<I, O, E> {

    fun launch(scope: CoroutineScope, param: I, callback: ((Either<E, O>) -> Unit)? = null) {
        scope.launch {
            callback?.invoke(execute(param))
        }
    }

    abstract suspend fun execute(param: I): Either<E, O>

}

abstract class SimpleCoroutineUseCase<I, O> : CoroutineUseCase<I, O, Throwable>() {

    override suspend fun execute(param: I): Either<Throwable, O> {
        return try {
            val it = executes(param)
            Either.Right(it)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    abstract suspend fun executes(param: I): O

}