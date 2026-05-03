package com.uzuu.admin.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.data.session.SessionManager
import com.uzuu.admin.domain.model.ScanHistory
import com.uzuu.admin.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileUiEvent>(extraBufferCapacity = 5)
    val event = _event.asSharedFlow()

    fun init() {
        _state.update { it.copy(username = SessionManager.getUsername() ?: "") }
        loadProfile()
        observeScanHistory()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = profileRepository.getProfile()) {
                is ApiResult.Success -> {
                    _state.update { it.copy(profile = result.data, isLoading = false) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ProfileUiEvent.ShowError(result.message))
                }
            }
        }
    }

    private fun observeScanHistory() {
        viewModelScope.launch {
            profileRepository.getRecentScans(50).collect { history ->
                _state.update { it.copy(scanHistory = history, isScanHistoryLoading = false) }
            }
        }
    }

    fun onEditProfileClick() {
        viewModelScope.launch {
            _event.emit(ProfileUiEvent.ShowEditProfileDialog)
        }
    }

    fun updateProfile(fullName: String?, email: String?, phone: String?, address: String?) {
        if (fullName.isNullOrBlank() && email.isNullOrBlank() && phone.isNullOrBlank() && address.isNullOrBlank()) {
            viewModelScope.launch {
                _event.emit(ProfileUiEvent.ShowError("Vui lòng nhập ít nhất một thông tin"))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = profileRepository.updateProfile(fullName, email, phone, address)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(profile = result.data, isLoading = false) }
                    _event.emit(ProfileUiEvent.ShowMessage("Cập nhật thông tin thành công"))
                    _event.emit(ProfileUiEvent.ProfileUpdated)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ProfileUiEvent.ShowError(result.message))
                }
            }
        }
    }

    fun onChangePasswordClick() {
        viewModelScope.launch {
            _event.emit(ProfileUiEvent.ShowChangePasswordDialog)
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        if (oldPassword.isBlank()) {
            viewModelScope.launch {
                _event.emit(ProfileUiEvent.ShowError("Vui lòng nhập mật khẩu cũ"))
            }
            return
        }
        if (newPassword.isBlank()) {
            viewModelScope.launch {
                _event.emit(ProfileUiEvent.ShowError("Vui lòng nhập mật khẩu mới"))
            }
            return
        }
        if (oldPassword == newPassword) {
            viewModelScope.launch {
                _event.emit(ProfileUiEvent.ShowError("Mật khẩu mới không được trùng với mật khẩu cũ"))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = profileRepository.changePassword(oldPassword, newPassword)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ProfileUiEvent.ShowMessage("Đổi mật khẩu thành công"))
                    _event.emit(ProfileUiEvent.PasswordChanged)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ProfileUiEvent.ShowError(result.message))
                }
            }
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            _event.emit(ProfileUiEvent.ShowLogoutConfirmation)
        }
    }

    fun onLogout() {
        SessionManager.clear()
        viewModelScope.launch {
            _event.emit(ProfileUiEvent.NavigateToLogin)
        }
    }

    fun saveScanHistory(ticketCode: String, ticketName: String?, guestName: String?, eventName: String?, status: String = "SUCCESS", message: String? = null) {
        viewModelScope.launch {
            val scanHistory = ScanHistory(
                ticketCode = ticketCode,
                ticketName = ticketName,
                guestName = guestName,
                eventName = eventName,
                checkInTime = System.currentTimeMillis(),
                status = status,
                message = message
            )
            profileRepository.saveScanHistory(scanHistory)
        }
    }

    fun clearScanHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isScanHistoryLoading = true) }
            when (val result = profileRepository.clearScanHistory()) {
                is ApiResult.Success -> {
                    _state.update { it.copy(scanHistory = emptyList(), isScanHistoryLoading = false) }
                    _event.emit(ProfileUiEvent.ShowMessage("Xóa lịch sử thành công"))
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isScanHistoryLoading = false) }
                    _event.emit(ProfileUiEvent.ShowError(result.message))
                }
            }
        }
    }
}
