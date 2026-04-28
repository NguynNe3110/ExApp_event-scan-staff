package com.uzuu.admin.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.model.Register
import com.uzuu.admin.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegisterUiEvent>(extraBufferCapacity = 3)
    val event = _event.asSharedFlow()

    fun onRegister(fullName: String, username: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            // Validate
            if (fullName.isBlank() || username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                _event.emit(RegisterUiEvent.Toast("Vui lòng nhập đầy đủ thông tin"))
                return@launch
            }

            if (password != confirmPassword) {
                _event.emit(RegisterUiEvent.Toast("Mật khẩu xác nhận không khớp"))
                return@launch
            }

            if (password.length < 6) {
                _event.emit(RegisterUiEvent.Toast("Mật khẩu phải có ít nhất 6 ký tự"))
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            when (val result = authRepo.register(Register(username, password, email, fullName))) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(RegisterUiEvent.Toast("Đăng ký thành công! Vui lòng đăng nhập."))
                    _event.emit(RegisterUiEvent.NavigateToLogin)
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(RegisterUiEvent.Toast(result.message))
                }
            }
        }
    }
}
