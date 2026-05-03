package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.ChangePasswordRequestDto
import com.uzuu.admin.data.remote.dto.request.UpdateProfileRequestDto
import com.uzuu.admin.data.remote.dto.response.ChangePasswordResponseDto
import com.uzuu.admin.data.remote.dto.response.UpdateProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProfileApi {

    @GET("users/profile")
    suspend fun getProfile(): BaseResponseDto<UpdateProfileResponseDto>

    @PUT("users/profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequestDto
    ): BaseResponseDto<UpdateProfileResponseDto>

    @POST("users/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequestDto
    ): BaseResponseDto<ChangePasswordResponseDto>
}
