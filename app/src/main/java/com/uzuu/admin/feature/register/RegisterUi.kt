package com.uzuu.admin.feature.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val fullName: String = ""
)

sealed class RegisterUiEvent {
    data class Toast(val message: String) : RegisterUiEvent()
    object NavigateToLogin : RegisterUiEvent()
}
