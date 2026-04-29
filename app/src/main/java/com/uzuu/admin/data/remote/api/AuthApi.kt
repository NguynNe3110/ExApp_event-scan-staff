package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.ForgotPasswordRequestDto
import com.uzuu.admin.data.remote.dto.request.LoginRequestDto
import com.uzuu.admin.data.remote.dto.request.RegisterRequestDto
import com.uzuu.admin.data.remote.dto.request.VerifyOtpRequestDto
import com.uzuu.admin.data.remote.dto.response.ForgotPasswordResponseDto
import com.uzuu.admin.data.remote.dto.response.RegisterResponseDto
import com.uzuu.admin.data.remote.dto.response.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): BaseResponseDto<TokenResponseDto>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): BaseResponseDto<RegisterResponseDto>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequestDto
    ): BaseResponseDto<ForgotPasswordResponseDto>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequestDto
    ): BaseResponseDto<ForgotPasswordResponseDto>

    @POST("auth/resend-otp")
    suspend fun resendOtp(
        @Body request: ForgotPasswordRequestDto
    ): BaseResponseDto<ForgotPasswordResponseDto>
}