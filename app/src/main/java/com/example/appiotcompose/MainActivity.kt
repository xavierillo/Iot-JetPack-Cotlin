package com.example.appiotcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.appiotcompose.nav.AppNavGraph
import com.example.appiotcompose.ui.theme.AppIotComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // sin keepSplash ni delay manual

        super.onCreate(savedInstanceState)

        setContent {
            AppIotComposeTheme {
                AppNavGraph()
            }
        }
    }
}
