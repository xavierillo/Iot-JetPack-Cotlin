package com.example.appiotcompose.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.appiotcompose.screens.HomeScreen
import com.example.appiotcompose.screens.LoginScreen
import com.example.appiotcompose.screens.RegisterScreen
import com.example.appiotcompose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//@Composable
//fun AppNavGraph() {
//    val nav = rememberNavController()
//    NavHost(navController = nav, startDestination = Route.Login.path) {
//        composable(Route.Login.path)    { LoginScreen(nav) }
//        composable(Route.Register.path) { RegisterScreen(nav) }
//        composable(Route.Home.path)     { HomeScreen() }
//    }
//}

class AppStartViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            // 👇 Simula API 1.5 s
            delay(3500L)
            _isLoading.value = false
        }
    }
}

@Composable
fun AppNavGraph(vm: AppStartViewModel = viewModel()) {
    val nav = rememberNavController()
    val isLoading by vm.isLoading.collectAsState()

    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") {
            SplashLottie(isLoading = isLoading) {
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
fun SplashLottie(
    isLoading: Boolean,
    onFinish: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_lottie))
    val animState = animateLottieCompositionAsState(composition, iterations = Int.MAX_VALUE)

    // Navega cuando termine la "carga"
    LaunchedEffect(isLoading) {
        if (!isLoading) onFinish()
    }

    Box(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        if (composition == null) {
            CircularProgressIndicator()
        } else {
            LottieAnimation(composition, { animState.progress }, Modifier.size(220.dp))
        }
    }
}


