package com.cobasendiri.kasirmudahkmp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cobasendiri.kasirmudahkmp.core.data.gallery.GallerySaver
import com.cobasendiri.kasirmudahkmp.core.data.gallery.IOSGallerySaver
import com.cobasendiri.kasirmudahkmp.core.data.preference.createIOSDatastore
import com.cobasendiri.kasirmudahkmp.core.data.room.db.DatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.IOSDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DatabaseBuilder> { IOSDatabaseBuilder() }
    single<GallerySaver> { IOSGallerySaver() }
    single<DataStore<Preferences>> { createIOSDatastore() }
}