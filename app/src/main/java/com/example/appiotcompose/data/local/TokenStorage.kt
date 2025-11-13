package com.example.appiotcompose.data.local

// data/local/TokenStorage.kt
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.dataStore by preferencesDataStore("auth_prefs")
object TokenStorage {
    private val KEY_TOKEN = stringPreferencesKey("auth_token")

    fun tokenFlow(ctx: Context): Flow<String?> =
        ctx.dataStore.data.map { it[KEY_TOKEN] }

    suspend fun saveToken(ctx: Context, token: String) {
        ctx.dataStore.edit { it[KEY_TOKEN] = token }
    }

    suspend fun clear(ctx: Context) {
        ctx.dataStore.edit { it.remove(KEY_TOKEN) }
    }
}