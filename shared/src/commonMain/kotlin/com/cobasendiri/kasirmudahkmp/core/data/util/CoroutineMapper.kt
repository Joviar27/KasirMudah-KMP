package com.cobasendiri.kasirmudahkmp.core.data.util

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.data.util.ExceptionMapper.asKasirMudahException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

object CoroutineMapper{
    suspend fun <T> runMapExceptionSuspending(
        log: Pair<String, String>? = null,
        block: suspend () -> T
    ): T {
        return try {
            block()
        }catch (e: Exception){
            log?.let {
                Logger.withTag(it.first).e { "${it.second}: ${e.message}" }
            }
            throw e.asKasirMudahException()
        }
    }

    fun <U,T>Flow<U>.mapExceptionFlow(
        log: Pair<String, String>? = null,
        dataMapping: (U) -> T
    ): Flow<T?>{
        return this.map{
            try {
                dataMapping(it)
            }catch (e: Throwable){
                //Catch operation logic, stream continue
                null
            }
        }.catch { exception ->
            //Catch data source error, stream cancelled
            log?.let {
                Logger.withTag(it.first).e { "${it.second}: ${exception.message}" }
            }
            throw exception.asKasirMudahException()
        }
    }

    fun <T> Flow<T>.mapExceptionFlow(
        log: Pair<String, String>? = null
    ): Flow<T> {
        return this.catch { exception ->
            log?.let {
                Logger.withTag(it.first).e { "${it.second}: ${exception.message}" }
            }
            throw exception.asKasirMudahException()
        }
    }
}
