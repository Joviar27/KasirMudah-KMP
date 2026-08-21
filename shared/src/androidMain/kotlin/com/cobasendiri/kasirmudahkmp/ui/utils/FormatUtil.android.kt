package com.cobasendiri.kasirmudahkmp.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

actual fun Long.dateFormat(shortFormat: Boolean): String {
    return try {
        val format = if(shortFormat) "dd MMMM yyyy" else "dd MMMM yyyy - HH:mm:ss"
        val date = Date(this * 1000)
        val formatter = SimpleDateFormat(format, Locale("id", "ID"))
        formatter.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        return formatter.format(date)
    }catch (e: Exception){
        "Invalid date"
    }
}