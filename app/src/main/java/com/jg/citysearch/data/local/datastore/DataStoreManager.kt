package com.jg.citysearch.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val context: Context,
) {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val dataStore = context.dataStore

    companion object {
        private val CITIES_DOWNLOADED = booleanPreferencesKey("cities_downloaded")
    }

    suspend fun setCitiesDownloaded(downloaded: Boolean) {
        dataStore.edit { prefs ->
            prefs[CITIES_DOWNLOADED] = downloaded
        }
    }

    val isCitiesDownloaded: Flow<Boolean> = dataStore.data
        .map { prefs -> prefs[CITIES_DOWNLOADED] == true }

    suspend fun clearDownloadFlag() {
        dataStore.edit { prefs -> prefs[CITIES_DOWNLOADED] = false }
    }
}