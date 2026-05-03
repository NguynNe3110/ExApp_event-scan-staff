package com.uzuu.admin.domain.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.model.Profile
import com.uzuu.admin.domain.model.ScanHistory
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): ApiResult<Profile>
    suspend fun updateProfile(fullName: String?, email: String?, phone: String?, address: String?): ApiResult<Profile>
    suspend fun changePassword(oldPassword: String, newPassword: String): ApiResult<String>
    
    // Scan History
    suspend fun saveScanHistory(scanHistory: ScanHistory): ApiResult<Long>
    fun getAllScanHistory(): Flow<List<ScanHistory>>
    fun getRecentScans(limit: Int): Flow<List<ScanHistory>>
    suspend fun clearScanHistory(): ApiResult<Int>
}
