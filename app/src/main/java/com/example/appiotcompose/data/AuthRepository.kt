package com.example.appiotcompose.data

import android.content.Context
import com.example.appiotcompose.data.local.TokenStorage
import com.example.appiotcompose.data.remote.AuthApi
import com.example.appiotcompose.data.remote.HttpClient
import com.example.appiotcompose.data.remote.dto.LoginRequest
import com.example.appiotcompose.data.remote.dto.LoginResponse
import com.example.appiotcompose.data.remote.dto.UserDto


// data/AuthRepository.kt

class AuthRepository(
    private val api: AuthApi = HttpClient.authApi
) {
    suspend fun login(ctx: Context, email: String, password: String): Result<UserDto> {
        return try {
            val res = api.login(LoginRequest(email, password))
            TokenStorage.saveToken(ctx, res.token)
            Result.success(res.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}