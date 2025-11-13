package com.example.appiotcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appiotcompose.nav.Route
import com.example.appiotcompose.ui.theme.AppIotComposeTheme
import com.example.appiotcompose.R
import com.example.appiotcompose.screens.login.AuthViewModel
import com.example.appiotcompose.screens.login.LoginUiState

@Composable
fun LoginContent(
    email: String,
    pass: String,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_d), // nombre del archivo
            contentDescription = "Logo de la app",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text("Bienvenido",
            fontSize = 23.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(20.dp))


        Spacer(Modifier.height(12.dp))
        OutlinedTextField( email,
            onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pass,
            onPassChange,
            label = { Text("Contraseña") }, singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onLoginClick },
            enabled = uiState !is LoginUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (uiState is LoginUiState.Loading) "Ingresando..." else "Ingresar") }

        if (uiState is LoginUiState.Error) {
            Spacer(Modifier.height(8.dp))
            Text((uiState as LoginUiState.Error).message, color = MaterialTheme.colorScheme.error)
        }

        TextButton(onClick = onRegisterClick, modifier = Modifier.align(Alignment.End)) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

@Composable
fun LoginScreen(nav: NavController, vm: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val uiState by vm.ui.collectAsState()


    LoginContent(
        email, pass, uiState,
        onEmailChange = {  email = it },
        onPassChange = { pass = it },
        onLoginClick = { nav.navigate(Route.Home.path) },
        onRegisterClick = { nav.navigate(Route.Register.path) }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    AppIotComposeTheme {
        LoginContent(
            email = "javier@demo.cl",
            pass = "123456",
            uiState = LoginUiState.Idle,
            onEmailChange = {},
            onPassChange = {},
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}