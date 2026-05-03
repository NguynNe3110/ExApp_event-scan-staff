package com.uzuu.admin.feature.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.admin.domain.repository.CheckInRepository
import com.uzuu.admin.domain.repository.ProfileRepository

class ScanFactory(
    private val checkInRepo: CheckInRepository,
    private val profileRepo: ProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScanViewModel(checkInRepo, profileRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}