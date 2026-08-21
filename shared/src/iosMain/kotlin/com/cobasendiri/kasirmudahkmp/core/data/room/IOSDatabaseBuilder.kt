package com.cobasendiri.kasirmudahkmp.core.data.room

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IOSDatabaseBuilder: DatabaseBuilder {

    override fun getDatabase(): ShopDatabase {
        val dbFilePath = documentDirectory() + "/Shop.db"
        return Room.databaseBuilder<ShopDatabase>(
            name = dbFilePath
        ).setDriver(BundledSQLiteDriver()).build()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String{
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null
        )
        return documentDirectory?.path ?: ""
    }
}