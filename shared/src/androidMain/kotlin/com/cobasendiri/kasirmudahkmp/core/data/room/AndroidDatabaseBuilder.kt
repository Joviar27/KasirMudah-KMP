package com.cobasendiri.kasirmudahkmp.core.data.room

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.cobasendiri.kasirmudahkmp.core.data.room.db.DatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.db.ShopDatabase

class AndroidDatabaseBuilder(private val context: Context): DatabaseBuilder {

    override fun getDatabase(): ShopDatabase {
        val dbFile = context.getDatabasePath("character.db")
        return Room.databaseBuilder<ShopDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver()).build()
    }
}