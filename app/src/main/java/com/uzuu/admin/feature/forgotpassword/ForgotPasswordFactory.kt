package com.uzuu.admin.feature.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.admin.domain.repository.AuthRepository

class ForgotPasswordFactory(
    private val authRepo: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForgotPasswordViewModel(authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
