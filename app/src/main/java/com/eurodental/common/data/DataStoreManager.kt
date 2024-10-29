package com.eurodental.common.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("app_preferences")

@Singleton
class DataStoreManager @Inject constructor(
    private val context: Context
) {


    suspend fun save(key : String, value : String) {
        context.dataStore.edit { preferences ->
            Log.d("AuthConfig","value = $value")

            preferences[stringPreferencesKey(key)] = value
            Log.d("AuthConfig","Saved token with key $key and value ${preferences[stringPreferencesKey(key)]}")
        }
    }

    fun retrieve(key : String) : Flow<String?> {
        Log.d("AuthConfig","retrieving data of key $key using retrieve fun")
        context.dataStore.data.map {
            Log.d("AuthConfig","pref keys ${it.asMap().keys}")
        }
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
    }

    fun retrieveAllPreferences() = run { context.dataStore.data }
}