package com.cobasendiri.kasirmudahkmp.core.data.util

import androidx.sqlite.SQLiteException
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException

object ExceptionMapper {

    fun Throwable.asKasirMudahException(): KasirMudahException {
        return when(this){
            is SQLiteException -> KasirMudahException.DatabaseError
            else -> KasirMudahException.UnknownError(this.message)
        }
    }
}