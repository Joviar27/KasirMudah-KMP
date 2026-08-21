package com.cobasendiri.kasirmudahkmp

import android.app.Application
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
    }
}