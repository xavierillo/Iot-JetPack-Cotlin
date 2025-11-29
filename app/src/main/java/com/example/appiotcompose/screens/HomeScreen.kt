package com.example.appiotcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appiotcompose.data.remote.dto.UserDto
import com.example.appiotcompose.screens.home.SensorUiState
import com.example.appiotcompose.screens.home.SensorViewModel
import com.example.appiotcompose.screens.home.getHumidityIcon
import com.example.appiotcompose.screens.home.getTempIcon
import com.example.appiotcompose.screens.login.AuthState
import com.example.appiotcompose.screens.login.AuthViewModel
import com.example.appiotcompose.ui.theme.AppIotComposeTheme
//screens/HomeScreen.kt
@Composable
fun LoginContent(
    authState: AuthState,
    sensorState: SensorUiState,
    onLogout: () -> Unit,
    onLedClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1) Mensaje de bienvenida
        if (authState is AuthState.Authenticated) {
            val user = authState.user
            Text(
                text = "Bienvenido ${user.name}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(20.dp))
        }

        // 2) Estado de sensores
        if (sensorState.isLoading && sensorState.temperature == null) {
            Text("Cargando datos de sensores...")
            Spacer(Modifier.height(12.dp))
        }

        sensorState.errorMessage?.let { msg ->
            Text(
                text = msg,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(12.dp))
        }

        // 3) Temperatura
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = getTempIcon(sensorState.temperature)),
                contentDescription = "Icono temperatura",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Temperatura")
                Text(
                    text = sensorState.temperature?.let { "$it °C" } ?: "-- °C",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // 4) Humedad
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = getHumidityIcon(sensorState.humidity)),
                contentDescription = "Icono humedad",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Humedad")
                Text(
                    text = sensorState.humidity?.let { "$it %" } ?: "-- %",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // 5) Última actualización
        sensorState.lastUpdate?.let { last ->
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Última actualización: $last",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLedClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Controlar LEDs IoT")
        }


        Spacer(modifier = Modifier.height(32.dp))

        // 6) Botón de logout
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
fun HomeScreen(
    vm: AuthViewModel = viewModel(),
    sensorVm: SensorViewModel = viewModel(),
    onLogoutDone: () -> Unit,
    onLedClick: () -> Unit
) {
    val authState by vm.authState.collectAsState()
    val sensorState = sensorVm.uiState.value  // uiState es mutableStateOf en el ViewModel

    // Si después de logout el estado cambia a Unauthenticated, navegar
    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            onLogoutDone()
        }
    }

    LoginContent(
        authState = authState,
        sensorState = sensorState,
        onLogout = { vm.logout() },
        onLedClick = onLedClick
    )
}
@Composable
fun HomeContentPreviewUI(
    authState: AuthState,
    sensorState: SensorUiState,
    onLogout: () -> Unit = {},
    onLedClick: () -> Unit = {}
) {
    LoginContent(
        authState = authState,
        sensorState = sensorState,
        onLogout = onLogout,
        onLedClick = onLedClick
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppIotComposeTheme {
        HomeContentPreviewUI(
            authState = AuthState.Authenticated(
                user = UserDto(
                    id = 1,
                    name = "Javier Ahumada",
                    email = "javier@example.com"
                )
            ),
            sensorState = SensorUiState(
                isLoading = false,
                temperature = 24.5f,
                humidity = 60f,
                lastUpdate = "2025-11-22 18:30",
                errorMessage = null
            ),
            onLogout = {},
            onLedClick = {}
        )
    }
}