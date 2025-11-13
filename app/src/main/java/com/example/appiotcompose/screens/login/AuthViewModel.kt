package com.example.appiotcompose.screens.login

// screens/login/AuthViewModel.kt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appiotcompose.data.AuthRepository
import com.example.appiotcompose.data.remote.dto.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    data class Success(val user: UserDto) : LoginUiState()
}

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = AuthRepository()
    private val _ui = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val ui: StateFlow<LoginUiState> = _ui

    fun login(email: String, password: String) {
        _ui.value = LoginUiState.Loading
        viewModelScope.launch {
            val ctx = getApplication<Application>().applicationContext
            val res = repo.login(ctx, email, password)
            _ui.value = res.fold(
                onSuccess = { LoginUiState.Success(it) },
                onFailure = { LoginUiState.Error(it.message ?: "Error de login") }
            )
        }
    }
}
