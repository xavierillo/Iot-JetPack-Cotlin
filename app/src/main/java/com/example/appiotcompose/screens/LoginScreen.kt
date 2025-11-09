package com.example.appiotcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appiotcompose.nav.Route
import com.example.appiotcompose.ui.theme.AppIotComposeTheme
import com.example.appiotcompose.R

@Composable
fun LoginContent(
    user: String,
    pass: String,
    onUserChange: (String) -> Unit,
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
        OutlinedTextField(user,
            onUserChange,
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pass,
            onPassChange,
            label = { Text("Contraseña") }
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth()) {
            Text("Ingresar")
        }
        TextButton(onClick = onRegisterClick, modifier = Modifier.align(Alignment.End)) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

@Composable
fun LoginScreen(nav: NavController) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    LoginContent(
        user, pass,
        onUserChange = { user = it },
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
            user = "javier@demo.cl",
            pass = "123456",
            onUserChange = {},
            onPassChange = {},
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}