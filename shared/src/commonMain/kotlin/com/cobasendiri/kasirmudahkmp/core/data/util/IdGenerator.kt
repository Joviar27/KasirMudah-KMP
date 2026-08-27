package com.cobasendiri.kasirmudahkmp.core.data.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.uuid.Uuid

object IdGenerator {

    fun generateProductId(): String{
        return try {
            val formattedDate = getDateForId()
            val randomUuid = Uuid.random().toString()

            "$formattedDate-product-$randomUuid"
        }catch (e: Exception){
            "product-${Uuid.random()}"
        }
    }

    fun generateTransactionId(): String{
        return try {
            val date = getDateForId()
            val randomUuid = Uuid.random().toString()

            "${date.take(8)}$randomUuid${date.takeLast(6)}"
        }catch (e: Exception){
            Uuid.random().toString()
        }
    }

    private fun getDateForId(): String{
        val currentDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(
            TimeZone.currentSystemDefault()
        )

        val customFormat = LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            this@Format.day(padding = Padding.ZERO)
            char(' ')
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
        return currentDateTime.format(customFormat)
    }
}