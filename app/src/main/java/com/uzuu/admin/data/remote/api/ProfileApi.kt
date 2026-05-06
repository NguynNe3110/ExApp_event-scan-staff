package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.ChangePasswordRequestDto
import com.uzuu.admin.data.remote.dto.response.ChangePasswordResponseDto
import com.uzuu.admin.data.remote.dto.response.OrganizerResponse
import com.uzuu.admin.data.remote.dto.response.UpdateProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {

    @GET("users/my-info")
    suspend fun getProfile(): BaseResponseDto<UpdateProfileResponseDto>

    @GET("users/staff/organizer/{organizerId}")
    suspend fun getOrganizer(
        @Path("organizerId") organizerId: Long
    ): BaseResponseDto<OrganizerResponse>

    @POST("users/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequestDto
    ): BaseResponseDto<ChangePasswordResponseDto>
}
