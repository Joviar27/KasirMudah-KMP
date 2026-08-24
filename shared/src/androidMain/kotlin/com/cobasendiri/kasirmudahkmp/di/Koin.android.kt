package com.cobasendiri.kasirmudahkmp.di

import com.cobasendiri.kasirmudahkmp.core.data.room.AndroidDatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.db.DatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DatabaseBuilder> { AndroidDatabaseBuilder(androidContext()) }
}