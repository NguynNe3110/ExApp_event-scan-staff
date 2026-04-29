package com.uzuu.admin.feature.verifyotp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerifyOtpViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VerifyOtpUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<VerifyOtpUiEvent>(extraBufferCapacity = 3)
    val event = _event.asSharedFlow()

    fun setEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onVerify(otp: String) {
        viewModelScope.launch {
            if (otp.isBlank() || otp.length != 6) {
                _event.emit(VerifyOtpUiEvent.Toast("Vui lòng nhập mã OTP gồm 6 số"))
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            when (val result = authRepo.verifyOtp(_state.value.email, otp)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(VerifyOtpUiEvent.Toast("Xác thực thành công! Vui lòng đăng nhập lại"))
                    _event.emit(VerifyOtpUiEvent.NavigateToLogin)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(VerifyOtpUiEvent.Toast(result.message))
                }
            }
        }
    }

    fun onResendOtp() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = authRepo.resendOtp(_state.value.email)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(VerifyOtpUiEvent.Toast("Mã OTP mới đã được gửi đến email của bạn"))
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(VerifyOtpUiEvent.Toast(result.message))
                }
            }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _event.emit(VerifyOtpUiEvent.NavigateBack)
        }
    }
}
