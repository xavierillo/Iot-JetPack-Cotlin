package com.example.appiotcompose.data

import com.example.appiotcompose.data.remote.HttpClient
import com.example.appiotcompose.data.remote.dto.SensorDataDto

class SensorRepository {
    private val api = HttpClient.sensorApi

    suspend fun getSensorData(): Result<SensorDataDto> {
        return try {
            val data = api.getSensorData()
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
