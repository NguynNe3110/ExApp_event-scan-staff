package com.uzuu.admin.data.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.core.result.safeApiCall
import com.uzuu.admin.data.local.datasource.ScanHistoryLocalDataSource
import com.uzuu.admin.data.remote.datasource.ProfileRemoteDataSource
import com.uzuu.admin.data.remote.dto.request.ChangePasswordRequestDto
import com.uzuu.admin.data.remote.dto.request.UpdateProfileRequestDto
import com.uzuu.admin.domain.model.Profile
import com.uzuu.admin.domain.model.ScanHistory
import com.uzuu.admin.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val remote: ProfileRemoteDataSource,
    private val scanHistoryLocal: ScanHistoryLocalDataSource
) : ProfileRepository {

    override suspend fun getProfile(): ApiResult<Profile> = safeApiCall {
        val response = remote.getProfile()
        if (response.code == 200 || response.code == 0 || response.code == 1000) {
            val data = response.result
            Profile(
                id = data?.id,
                username = data?.username ?: "",
                email = data?.email ?: "",
                fullName = data?.fullName ?: "",
                phone = data?.phone ?: "",
                address = data?.address ?: "",
                role = data?.role ?: ""
            )
        } else {
            throw Exception(response.message ?: "Lấy thông tin tài khoản thất bại")
        }
    }

    override suspend fun updateProfile(
        fullName: String?,
        email: String?,
        phone: String?,
        address: String?
    ): ApiResult<Profile> = safeApiCall {
        val request = UpdateProfileRequestDto(
            fullName = fullName,
            email = email,
            phone = phone,
            address = address
        )
        val response = remote.updateProfile(request)
        if (response.code == 200 || response.code == 0 || response.code == 1000) {
            val data = response.result
            Profile(
                id = data?.id,
                username = data?.username ?: "",
                email = data?.email ?: "",
                fullName = data?.fullName ?: "",
                phone = data?.phone ?: "",
                address = data?.address ?: "",
                role = data?.role ?: ""
            )
        } else {
            throw Exception(response.message ?: "Cập nhật thông tin thất bại")
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): ApiResult<String> =
        safeApiCall {
            val request = ChangePasswordRequestDto(oldPassword, newPassword)
            val response = remote.changePassword(request)
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                response.message ?: "Đổi mật khẩu thành công"
            } else {
                throw Exception(response.message ?: "Đổi mật khẩu thất bại")
            }
        }

    override suspend fun saveScanHistory(scanHistory: ScanHistory): ApiResult<Long> = safeApiCall {
        val entity = scanHistory.toEntity()
        scanHistoryLocal.insertScanHistory(entity)
    }

    override fun getAllScanHistory(): Flow<List<ScanHistory>> {
        return scanHistoryLocal.getAllScanHistory().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getRecentScans(limit: Int): Flow<List<ScanHistory>> {
        return scanHistoryLocal.getRecentScans(limit).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun clearScanHistory(): ApiResult<Int> = safeApiCall {
        scanHistoryLocal.clearAll()
    }
}

// Mapper extensions
private fun ScanHistory.toEntity() = com.uzuu.admin.data.local.entity.ScanHistoryEntity(
    id = this.id,
    ticketCode = this.ticketCode,
    ticketName = this.ticketName,
    guestName = this.guestName,
    eventName = this.eventName,
    checkInTime = this.checkInTime,
    status = this.status,
    message = this.message
)

private fun com.uzuu.admin.data.local.entity.ScanHistoryEntity.toModel() = ScanHistory(
    id = this.id,
    ticketCode = this.ticketCode,
    ticketName = this.ticketName,
    guestName = this.guestName,
    eventName = this.eventName,
    checkInTime = this.checkInTime,
    status = this.status,
    message = this.message
)
