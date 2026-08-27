package com.cobasendiri.kasirmudahkmp.core.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfilePreferenceManager(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val SHOP_NAME_KEY = stringPreferencesKey("shop_name")
        val SHOP_IMAGE_KEY = stringPreferencesKey("shop_image")
    }

    suspend fun saveShopProfile(shopName: String, shopImage: String){
        dataStore.edit { preferences ->
            preferences[SHOP_NAME_KEY] = shopName
            preferences[SHOP_IMAGE_KEY] = shopImage
        }
    }

    val shopNameFlow: Flow<String?> =
        dataStore.data.map { preference ->
            preference[SHOP_NAME_KEY]
        }

    val shopImageFlow: Flow<String?> =
        dataStore.data.map { preference ->
            preference[SHOP_IMAGE_KEY]
        }
}