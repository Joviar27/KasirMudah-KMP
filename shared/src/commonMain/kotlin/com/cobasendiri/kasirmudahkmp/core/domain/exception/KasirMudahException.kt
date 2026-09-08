package com.cobasendiri.kasirmudahkmp.core.domain.exception

sealed class KasirMudahException() : Exception() {

    data object TransactionAmountInvalidError : KasirMudahException() {
        private fun readResolve(): Any = TransactionAmountInvalidError
    }

    data object InvalidInputError : KasirMudahException() {
        private fun readResolve(): Any = InvalidInputError
    }

    data object DatabaseError : KasirMudahException() {
        private fun readResolve(): Any = DatabaseError
    }

    data object GalleryPermissionError : KasirMudahException(){
        private fun readResolve(): Any = GalleryPermissionError
    }
    data class UnknownError(val originalMessage: String?) : KasirMudahException()

}
