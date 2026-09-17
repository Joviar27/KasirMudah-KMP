package com.cobasendiri.kasirmudahkmp

import android.app.Application
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.cobasendiri.kasirmudahkmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidLogger()
            androidContext(this@MainApplication)
        }

        if(BuildConfig.DEBUG){
            Logger.setMinSeverity(Severity.Debug)
            Logger.setLogWriters(LogcatWriter())
        }else{
            Logger.setLogWriters(emptyList())
        }
    }
}