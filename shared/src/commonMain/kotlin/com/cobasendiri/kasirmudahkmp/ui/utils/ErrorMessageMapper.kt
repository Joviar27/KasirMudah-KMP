package com.cobasendiri.kasirmudahkmp.ui.utils

import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.error_database
import kasirmudah_kmp.shared.generated.resources.error_general
import kasirmudah_kmp.shared.generated.resources.error_input_invalid
import kasirmudah_kmp.shared.generated.resources.error_transaction_amount_invalid
import kotlin.uuid.Uuid

object ErrorMessageMapper {
    fun Throwable.asUiMessage(): UiMessage{
        val getRandomId = Uuid.random().toLongs { mostSignificantBits, _ ->
            mostSignificantBits
        }
        return when(this){
            is KasirMudahException.DatabaseError -> {
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, Res.string.error_database
                )
            }
            is KasirMudahException.UnknownError ->{
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, Res.string.error_general
                )
            }
            is KasirMudahException.TransactionAmountInvalidError ->{
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, Res.string.error_transaction_amount_invalid
                )
            }
            is KasirMudahException.InvalidInputError ->{
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, Res.string.error_input_invalid
                )
            }
            else -> UiMessage.DynamicString(
                getRandomId, UiMessageType.ERROR, this.message ?: "Something went wrong"
            )
        }
    }
}