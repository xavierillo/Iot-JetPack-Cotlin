package com.example.appiotcompose.data.local

// data/local/TokenStorage.kt
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("auth_prefs")
object TokenStorage {
    private val KEY_TOKEN = stringPreferencesKey("auth_token")

    suspend fun saveToken(ctx: Context, token: String) {
        ctx.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    suspend fun getToken(ctx: Context): String? {
        return ctx.dataStore.data
            .map { it[KEY_TOKEN] }
            .first()
    }

    suspend fun clearToken(ctx: Context) {
        ctx.dataStore.edit { it.remove(KEY_TOKEN) }
    }
}