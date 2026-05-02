package com.satyasetu.app.ui.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val village: String = "",
    val district: String = "",
    val state: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = authRepository.observeAuthState()
        .map { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = authRepository.isLoggedIn
        )

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name, error = null) }
    }

    fun updateVillage(village: String) {
        _uiState.update { it.copy(village = village, error = null) }
    }

    fun updateDistrict(district: String) {
        _uiState.update { it.copy(district = district, error = null) }
    }

    fun updateState(state: String) {
        _uiState.update { it.copy(state = state, error = null) }
    }

    fun clearSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun login() {
        val state = _uiState.value
        if (state.isLoading) return

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(error = "Please enter both email and password.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            authRepository.loginWithEmail(state.email.trim(), state.password)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message ?: "Login failed.")
                    }
                }
        }
    }

    fun register() {
        val state = _uiState.value
        if (state.isLoading) return

        if (state.email.isBlank() || state.password.isBlank() || state.name.isBlank()) {
            _uiState.update { it.copy(error = "Please fill in the required fields.") }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches()) {
            _uiState.update { it.copy(error = "Please enter a valid email address.") }
            return
        }

        if (state.password.length < 6) {
            _uiState.update { it.copy(error = "Password must be at least 6 characters.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            authRepository.registerWithEmail(
                email = state.email.trim(),
                password = state.password,
                name = state.name.trim(),
                village = state.village.trim(),
                district = state.district.trim(),
                state = state.state.trim()
            ).onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(isLoading = false, error = error.message ?: "Registration failed.")
                }
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
        _uiState.update { AuthUiState() }
    }
}
