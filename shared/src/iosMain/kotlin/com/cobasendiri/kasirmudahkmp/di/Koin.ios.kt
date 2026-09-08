package com.cobasendiri.kasirmudahkmp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cobasendiri.kasirmudahkmp.core.data.gallery.AppSettingHandler
import com.cobasendiri.kasirmudahkmp.core.data.gallery.GallerySaver
import com.cobasendiri.kasirmudahkmp.core.data.gallery.GallerySaverBridge
import com.cobasendiri.kasirmudahkmp.core.data.gallery.IOSAppSettingHandler
import com.cobasendiri.kasirmudahkmp.core.data.preference.createIOSDatastore
import com.cobasendiri.kasirmudahkmp.core.data.room.db.DatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.IOSDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DatabaseBuilder> { IOSDatabaseBuilder() }
    single<GallerySaver> { GallerySaverBridge.getGallerySaver() }
    single<DataStore<Preferences>> { createIOSDatastore() }
    single<AppSettingHandler> { IOSAppSettingHandler() }
}