package com.cobasendiri.kasirmudahkmp.di

import com.cobasendiri.kasirmudahkmp.core.data.room.db.DatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.IOSDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DatabaseBuilder> { IOSDatabaseBuilder() }
}