package com.uzuu.admin.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.admin.domain.repository.AuthRepository

class RegisterFactory(
    private val authRepo: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
