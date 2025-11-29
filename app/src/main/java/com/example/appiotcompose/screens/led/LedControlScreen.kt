package com.example.appiotcompose.screens.led

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedControlScreen(
    viewModel: LedViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    // Si quieres mostrar errores como SnackBar / Toast podrías usar esto
    // (en la guía puedes dejarlo como "actividad opcional").

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Control de LEDs IoT") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
            }

            Text("Control de 3 LEDs conectados a NodeMCU", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LED 1", modifier = Modifier.weight(1f))
                Switch(
                    checked = uiState.led1,
                    onCheckedChange = { viewModel.onToggleLed(1, it) }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("LED 2", modifier = Modifier.weight(1f))
                Switch(
                    checked = uiState.led2,
                    onCheckedChange = { viewModel.onToggleLed(2, it) }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("LED 3", modifier = Modifier.weight(1f))
                Switch(
                    checked = uiState.led3,
                    onCheckedChange = { viewModel.onToggleLed(3, it) }
                )
            }

            Spacer(Modifier.height(24.dp))

            uiState.error?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
