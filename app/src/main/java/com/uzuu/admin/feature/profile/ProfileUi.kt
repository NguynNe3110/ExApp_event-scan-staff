package com.uzuu.admin.feature.profile

data class ProfileUiState(
    val username: String = ""
)

sealed class ProfileUiEvent {
    object NavigateToLogin : ProfileUiEvent()
}
