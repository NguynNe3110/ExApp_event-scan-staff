package com.uzuu.admin.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.admin.domain.repository.ProfileRepository

class ProfileFactory(
    private val profileRepo: ProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
