package com.uzuu.admin.feature.forgotpassword

data class ForgotPasswordUiState(
    val isLoading: Boolean = false,
    val email: String = ""
)

sealed class ForgotPasswordUiEvent {
    data class Toast(val message: String) : ForgotPasswordUiEvent()
    object NavigateToLogin : ForgotPasswordUiEvent()
}
