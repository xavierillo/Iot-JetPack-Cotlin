package com.example.appiotcompose.screens.home

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appiotcompose.data.remote.dto.UserDto
import com.example.appiotcompose.nav.NavigationBarUser
import com.example.appiotcompose.screens.login.AuthState
import com.example.appiotcompose.ui.theme.AppIotComposeTheme

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
        bottomBar = {
            NavigationBarUser(
                currentRoute = "home",
                onNavigate = onNavigate
            )
        },
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
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(
                    text = "Hola, $userName \uD83D\uDC4B", // Wave emoji
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // IoT Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEBF4FA)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Estado IoT",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sistema activo",
                        fontSize = 16.sp,
                        color = Color(0xFF3B77E1),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Última acción: Entrada",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickAccessItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEECE6)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.size(100.dp) // Square-ish
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun HomeUserContentPreviewUI(
    authState: AuthState,
    sensorState: SensorUiState,
    onLogout: () -> Unit = {},
    onLedClick: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    HomeUserContent(
        authState = authState,
        sensorState = sensorState,
        onLogout = onLogout,
        onLedClick = onLedClick,
        onNavigate = onNavigate
    )
}

@Preview(showBackground = true)
@Composable
fun HomeUserScreenPreview() {
    AppIotComposeTheme {
        HomeUserContentPreviewUI(
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
            onLedClick = {},
            onNavigate = {}
        )
    }
}
