package com.example.appiotcompose.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.example.appiotcompose.screens.HomeScreen
import com.example.appiotcompose.screens.LoginScreen
import com.example.appiotcompose.screens.RegisterScreen
import com.example.appiotcompose.R

//@Composable
//fun AppNavGraph() {
//    val nav = rememberNavController()
//    NavHost(navController = nav, startDestination = Route.Login.path) {
//        composable(Route.Login.path)    { LoginScreen(nav) }
//        composable(Route.Register.path) { RegisterScreen(nav) }
//        composable(Route.Home.path)     { HomeScreen() }
//    }
//}

@Composable
fun AppNavGraph() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                nav.navigate(Route.Login.path) {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable(Route.Login.path)    { LoginScreen(nav) }
        composable(Route.Register.path) { RegisterScreen(nav) }
        composable(Route.Home.path)     { HomeScreen() }
    }
}

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    // Composable minimal (logo centrado y fondo de marca)
    LaunchedEffect(Unit) {
        // Seguridad extra: si por alguna razón ya no mantiene el Splash nativo,
        // forzamos un fallback de 200-400ms para transicionar suave:
        kotlinx.coroutines.delay(250L)
        onFinish()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_d), // o Image con painterResource
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.Center)
        )
    }
}
