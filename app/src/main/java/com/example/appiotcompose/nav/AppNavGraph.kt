package com.example.appiotcompose.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.appiotcompose.screens.HomeScreen
import com.example.appiotcompose.screens.LoginScreen
import com.example.appiotcompose.screens.RegisterScreen
import com.example.appiotcompose.R
import com.example.appiotcompose.screens.led.LedControlScreen
import com.example.appiotcompose.screens.login.AuthState
import com.example.appiotcompose.screens.login.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(vm: AuthViewModel = viewModel()) {
    val nav = rememberNavController()
    val authState by vm.authState.collectAsState()

    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") {
            // AQUÍ reaccionamos a los cambios de authState
            LaunchedEffect(authState) {
                when (authState) {
                    AuthState.Checking -> {
                        // sigue en el splash, no hacemos nada
                    }
                    is AuthState.Authenticated -> {
                        nav.navigate(Route.Home.path) {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                    AuthState.Unauthenticated,
                    is AuthState.Error -> {
                        nav.navigate(Route.Login.path) {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
            }

            // Solo dibuja la animación
            SplashLottie()
        }

        composable(Route.Login.path) {
            LoginScreen(
                nav = nav,
                vm = vm
            )
        }

        composable(Route.Home.path) {
            HomeScreen(
                onLogoutDone = {
                    vm.logout()
                    nav.navigate(Route.Login.path) {
                        popUpTo(Route.Home.path) { inclusive = true }
                    }
                },
                onLedClick = {
                    nav.navigate(Route.LedControl.path)
                }
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(nav)
        }

        composable(Route.LedControl.path) {
            LedControlScreen()
        }

    }
}

@Composable
fun SplashLottie() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_lottie)
    )
    val animState = animateLottieCompositionAsState(
        composition,
        iterations = Int.MAX_VALUE
    )

    Box(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        if (composition == null) {
            CircularProgressIndicator()
        } else {
            LottieAnimation(
                composition = composition,
                progress = { animState.progress },
                modifier = Modifier.size(220.dp)
            )
        }
    }
}

@Composable
fun NavigationBarUser(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentRoute == "home", // Adjust route logic as needed
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = currentRoute == "users",
            onClick = { onNavigate("users") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Usuarios") },
            label = { Text("Usuarios") }
        )
        NavigationBarItem(
            selected = currentRoute == "keychains",
            onClick = { onNavigate("keychains") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Llaveros") },
            label = { Text("Llaveros") }
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}
