package com.example.appiotcompose.data.remote

// data/remote/AuthApi.kt
import com.example.appiotcompose.data.remote.dto.LoginRequest
import com.example.appiotcompose.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse
}
