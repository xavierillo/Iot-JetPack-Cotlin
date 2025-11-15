package com.example.appiotcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appiotcompose.nav.Route
import com.example.appiotcompose.screens.login.AuthViewModel
import com.example.appiotcompose.ui.theme.AppIotComposeTheme

@Composable
fun RegisterContent(
    name: String,
    email: String,
    pass: String,
    errorMsg: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onSendClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear cuenta", fontSize = 22.sp)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Nombre") })
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = onPassChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onSendClick,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Registrarse") }

        if (errorMsg.isNotEmpty()) {
            Text(errorMsg, color = Color.Red)
        }
    }
}



@Composable
fun RegisterScreen(nav: NavController, vm: AuthViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    RegisterContent(
        name,email, pass, errorMsg,
        onNameChange= { name = it },
        onEmailChange= { email = it },
        onPassChange= { pass = it },
        onSendClick = {
            vm.register(
                name = name,
                email = email,
                pass = pass,
                onSuccess = {
                    nav.navigate(Route.Login.path) {
                        popUpTo(Route.Register.path) { inclusive = true }
                    }
                },
                onFail = { errorMsg = it }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterContentPreview() {
    AppIotComposeTheme {
        RegisterContent(
            name = "javier",
            email = "javier@demo.cl",
            pass = "123456",
            errorMsg = "Error de registro",
            onNameChange = {},
            onEmailChange = {},
            onPassChange = {},
            onSendClick = {}
        )
    }
}