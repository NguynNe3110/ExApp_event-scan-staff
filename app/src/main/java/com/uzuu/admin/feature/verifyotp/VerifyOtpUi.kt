package com.uzuu.admin.feature.verifyotp

data class VerifyOtpUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val otp: String = ""
)

sealed class VerifyOtpUiEvent {
    data class Toast(val message: String) : VerifyOtpUiEvent()
    object NavigateToLogin : VerifyOtpUiEvent()
    object NavigateBack : VerifyOtpUiEvent()
}
