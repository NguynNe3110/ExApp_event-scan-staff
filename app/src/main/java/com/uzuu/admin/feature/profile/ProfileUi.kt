package com.uzuu.admin.feature.profile

import com.uzuu.admin.domain.model.Profile
import com.uzuu.admin.domain.model.ScanHistory

data class ProfileUiState(
    val username: String = "",
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val scanHistory: List<ScanHistory> = emptyList(),
    val isScanHistoryLoading: Boolean = false
)

sealed class ProfileUiEvent {
    object NavigateToLogin : ProfileUiEvent()
    data class ShowMessage(val message: String) : ProfileUiEvent()
    data class ShowError(val message: String) : ProfileUiEvent()
    object ShowEditProfileDialog : ProfileUiEvent()
    object ShowChangePasswordDialog : ProfileUiEvent()
    object ShowLogoutConfirmation : ProfileUiEvent()
    object ProfileUpdated : ProfileUiEvent()
    object PasswordChanged : ProfileUiEvent()
}
