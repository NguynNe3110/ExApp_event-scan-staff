package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.ChangePasswordRequestDto
import com.uzuu.admin.data.remote.dto.response.ChangePasswordResponseDto
import com.uzuu.admin.data.remote.dto.response.UpdateProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApi {

    @GET("users/my-info")
    suspend fun getProfile(): BaseResponseDto<UpdateProfileResponseDto>

    @POST("users/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequestDto
    ): BaseResponseDto<ChangePasswordResponseDto>
}
