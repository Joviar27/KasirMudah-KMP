package com.cobasendiri.kasirmudahkmp.ui.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localTimeZone
import platform.Foundation.timeZoneWithName

actual fun Long.dateFormat(shortFormat: Boolean): String {
    return try {
        val date = NSDate.dateWithTimeIntervalSince1970(this.toDouble())

        val formatter = NSDateFormatter().apply {
            dateFormat = if (shortFormat) "dd MMMM yyyy" else "dd MMMM yyyy - HH:mm:ss"
            locale = NSLocale(localeIdentifier = "id_ID")
            timeZone = NSTimeZone.timeZoneWithName("Asia/Jakarta")
                ?: NSTimeZone.localTimeZone
        }

        formatter.stringFromDate(date)
    } catch (e: Exception) {
        "Invalid date"
    }
}