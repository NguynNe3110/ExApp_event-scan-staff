package com.uzuu.admin.data.remote.datasource

import com.uzuu.admin.data.remote.api.ProfileApi
import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.ChangePasswordRequestDto
import com.uzuu.admin.data.remote.dto.request.UpdateProfileRequestDto
import com.uzuu.admin.data.remote.dto.response.ChangePasswordResponseDto
import com.uzuu.admin.data.remote.dto.response.UpdateProfileResponseDto

class ProfileRemoteDataSource(
    private val api: ProfileApi
) {
    suspend fun getProfile(): BaseResponseDto<UpdateProfileResponseDto> {
        return api.getProfile()
    }

    suspend fun updateProfile(request: UpdateProfileRequestDto): BaseResponseDto<UpdateProfileResponseDto> {
        return api.updateProfile(request)
    }

    suspend fun changePassword(request: ChangePasswordRequestDto): BaseResponseDto<ChangePasswordResponseDto> {
        return api.changePassword(request)
    }
}
