package com.example.appiotcompose.screens.home

import androidx.annotation.DrawableRes
import com.example.appiotcompose.R

//screens/home/SensorIcons.kt
@DrawableRes
fun getTempIcon(temperature: Float?): Int {
    if (temperature == null) return R.drawable.ic_temp_media

    return when {
        temperature < 15 -> R.drawable.ic_temp_baja
        temperature <= 28 -> R.drawable.ic_temp_media
        else -> R.drawable.ic_temp_alta
    }
}

@DrawableRes
fun getHumidityIcon(humidity: Float?): Int {
    if (humidity == null) return R.drawable.ic_hum_media

    return when {
        humidity < 30 -> R.drawable.ic_hum_baja
        humidity <= 60 -> R.drawable.ic_hum_alta
        else -> R.drawable.ic_hum_alta
    }
}