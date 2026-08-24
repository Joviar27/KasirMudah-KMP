package com.cobasendiri.kasirmudahkmp.ui.utils

expect fun Long.dateFormat(shortFormat: Boolean = false): String

object FormatUtil{
    fun String.decimalFormat(): String{
        return this.replace(Regex("(?<=\\d)(?=(\\d{3})+(?!\\d))"), ".")
    }

    fun String.rawFormat(): String{
        return this.replace(".", "")
    }
}
