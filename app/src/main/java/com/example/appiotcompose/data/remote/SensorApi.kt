package com.example.appiotcompose.data.remote

import com.example.appiotcompose.data.remote.dto.SensorDataDto
import retrofit2.http.GET

interface SensorApi {
    @GET("iot/data")
    suspend fun getSensorData(): SensorDataDto
}