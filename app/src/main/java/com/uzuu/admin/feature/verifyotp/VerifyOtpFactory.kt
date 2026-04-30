package com.uzuu.admin.feature.verifyotp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.admin.domain.repository.AuthRepository

class VerifyOtpFactory(
    private val authRepo: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerifyOtpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VerifyOtpViewModel(authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
