package com.example.appiotcompose.data.remote.dto
//data/remote/dto/AuthDto.kt
data class LoginRequest(
    val email: String,
    val password: String
)

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val user: UserDto
)