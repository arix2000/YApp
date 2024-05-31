package com.y.app.core.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.y.app.features.login.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "local")

    private val dataStore = context.dataStore

    companion object {
        val USER_USER_KEY = stringPreferencesKey("user")
    }

    val user: Flow<User?> = dataStore.data
        .map { preferences ->
            preferences[USER_USER_KEY]?.let { User.fromJson(it) }
        }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_USER_KEY] = user.toJson()
        }
    }
}