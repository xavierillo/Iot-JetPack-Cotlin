package com.example.appiotcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appiotcompose.nav.Route

@Composable
fun RegisterScreen(nav: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center) {
        Text("Crear cuenta", fontSize = 22.sp)
        OutlinedTextField(name, { name = it }, label = { Text("Nombre") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(email, { email = it }, label = { Text("Correo") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pwd, { pwd = it }, label = { Text("Contraseña") })
        Spacer(Modifier.height(16.dp))
        Button(onClick = { nav.navigate(Route.Login.path) }, modifier = Modifier.fillMaxWidth()) {
            Text("Crear cuenta")
        }
    }
}