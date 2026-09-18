package com.cobasendiri.kasirmudahkmp

import co.touchlab.kermit.Logger
import co.touchlab.kermit.OSLogWriter
import co.touchlab.kermit.Severity

fun initKermit(isDebug: Boolean) {
    if (isDebug) {
        Logger.setMinSeverity(Severity.Debug)
        Logger.setLogWriters(OSLogWriter())
    } else {
        Logger.setLogWriters(emptyList())
    }
}