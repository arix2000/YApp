package com.y.app.core.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "local")

    private val dataStore = context.dataStore

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("login")
        val USER_PASS_KEY = stringPreferencesKey("password")
    }

    val userLogin: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY]
        }

    val userPassword: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USER_PASS_KEY]
        }

    suspend fun saveUserCredentials(userName: String, password: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
            preferences[USER_PASS_KEY] = password
        }
    }
}