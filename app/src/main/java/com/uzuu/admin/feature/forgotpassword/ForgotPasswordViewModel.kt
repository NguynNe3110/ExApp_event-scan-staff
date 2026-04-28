package com.uzuu.admin.feature.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.model.ForgotPassword
import com.uzuu.admin.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ForgotPasswordUiEvent>(extraBufferCapacity = 3)
    val event = _event.asSharedFlow()

    fun onSubmit(email: String) {
        viewModelScope.launch {
            if (email.isBlank()) {
                _event.emit(ForgotPasswordUiEvent.Toast("Vui lòng nhập email"))
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _event.emit(ForgotPasswordUiEvent.Toast("Email không hợp lệ"))
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            when (val result = authRepo.forgotPassword(ForgotPassword(email))) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ForgotPasswordUiEvent.Toast("Yêu cầu khôi phục mật khẩu đã được gửi!"))
                    _event.emit(ForgotPasswordUiEvent.NavigateToLogin)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ForgotPasswordUiEvent.Toast(result.message))
                }
            }
        }
    }
}
