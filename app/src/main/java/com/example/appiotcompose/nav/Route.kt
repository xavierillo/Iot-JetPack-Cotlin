package com.example.appiotcompose.nav

sealed class Route(val path: String) {
    data object Login : Route("login")
    data object Register : Route("register")
    data object Home : Route("home")
    data object HomeUser : Route("homeUser")
    data object HomeAdmin : Route("homeAdmin")


    data object LedControl : Route("ledControl")
}