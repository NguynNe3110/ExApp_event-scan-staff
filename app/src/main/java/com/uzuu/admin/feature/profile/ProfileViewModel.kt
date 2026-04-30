package com.uzuu.admin.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.admin.data.session.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileUiEvent>(extraBufferCapacity = 3)
    val event = _event.asSharedFlow()

    fun init() {
        _state.update { it.copy(username = SessionManager.getUsername() ?: "") }
    }

    fun onLogout() {
        SessionManager.clear()
        viewModelScope.launch {
            _event.emit(ProfileUiEvent.NavigateToLogin)
        }
    }
}
