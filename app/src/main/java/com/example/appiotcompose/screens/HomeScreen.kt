package com.example.appiotcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appiotcompose.data.remote.dto.UserDto
import com.example.appiotcompose.nav.NavigationBarUser
import com.example.appiotcompose.screens.home.AdminMenuOption
import com.example.appiotcompose.screens.home.HomeAdmin3Screen
import com.example.appiotcompose.screens.home.QuickAccessItem
import com.example.appiotcompose.screens.home.SensorUiState
import com.example.appiotcompose.screens.home.SensorViewModel
import com.example.appiotcompose.screens.home.getHumidityIcon
import com.example.appiotcompose.screens.home.getTempIcon
import com.example.appiotcompose.screens.login.AuthState
import com.example.appiotcompose.screens.login.AuthViewModel
import com.example.appiotcompose.ui.theme.AppIotComposeTheme
//screens/HomeScreen.kt

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

    HomeContent(
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
    when (val state = authState) {
        is AuthState.Authenticated -> {
            if (state.user.role == "admin") {
                HomeAdminScreen(
                        authState = authState, // Placeholder
                        onNavigate = {}
                    )
            } else {
                HomeUserContent(
                    authState = authState,
                    sensorState = sensorState,
                    onLogout = onLogout,
                    onLedClick = onLedClick,
                    onNavigate = {  }
                )
            }
        }

        AuthState.Checking -> TODO()
        is AuthState.Error -> TODO()
        AuthState.Unauthenticated -> TODO()
    }
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
                    email = "javier@example.com",
                    role = "user"
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

@Composable
fun HomeContent(
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
fun HomeAdminScreen(
    authState: AuthState,
    onLogout: () -> Unit = {},
    onNavigate: (String) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFFDFBF7) // Creamy background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, // Placeholder for Admin avatar
                        contentDescription = "Admin Avatar",
                        tint = Color(0xFF5C93FF), // Blueish tint
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Hola, Admin \uD83D\uDC4B", // Wave emoji
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // System Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Estado del sistema",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Conectado a IoT",
                        fontSize = 16.sp,
                        color = Color(0xFF4CAF50), // Green for status
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "3 usuarios registrados",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "5 llaveros activos",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Menu Options
            AdminMenuOption(
                icon = Icons.Outlined.Person,
                label = "Gestión de Usuarios",
                onClick = { onNavigate("users_management") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AdminMenuOption(
                icon = Icons.Outlined.VpnKey,
                label = "Gestión de Llaveros",
                onClick = { onNavigate("keychains_management") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AdminMenuOption(
                icon = Icons.Outlined.Description,
                label = "Historial de accesos",
                onClick = { onNavigate("access_history") }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserContent(
    authState: AuthState,
    sensorState: SensorUiState,
    onLogout: () -> Unit,
    onLedClick: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val userName = (authState as? AuthState.Authenticated)?.user?.name?.split(" ")?.firstOrNull() ?: "Usuario"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Hola, $userName", // Wave emoji
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBarUser(
                currentRoute = "home",
                onNavigate = onNavigate
            )
        },
        containerColor = Color(0xFFF8F9FA) // Creamy background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // IoT Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEBF4FA)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2E9EEA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CarCrash,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Estado del sistema",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Llaveros vinculados: 3",
                        fontSize = 16.sp,
                        color = Color(0xFF3B77E1),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Última acción: Entrada 18:40 hrs, 14 dic ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Control Button
            Button(
                onClick = onLedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B77E1)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Controlar Puerta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quick Access Section
            Text(
                text = "Accesos rápidos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickAccessItem(
                    icon = Icons.Default.VpnKey,
                    label = "Mis llaveros",
                    onClick = { onNavigate("keychains") }
                )
                QuickAccessItem(
                    icon = Icons.Default.AccessTime,
                    label = "Historial",
                    onClick = { /* TODO */ }
                )
                QuickAccessItem(
                    icon = Icons.Default.Person,
                    label = "Mi perfil",
                    onClick = { onNavigate("profile") }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer Logo
            Text(
                text = "iotech",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}