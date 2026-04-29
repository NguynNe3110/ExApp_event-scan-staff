package com.uzuu.admin.data.remote.datasource

import com.uzuu.admin.data.remote.api.AuthApi
import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.ForgotPasswordRequestDto
import com.uzuu.admin.data.remote.dto.request.LoginRequestDto
import com.uzuu.admin.data.remote.dto.request.RegisterRequestDto
import com.uzuu.admin.data.remote.dto.request.VerifyOtpRequestDto
import com.uzuu.admin.data.remote.dto.response.ForgotPasswordResponseDto
import com.uzuu.admin.data.remote.dto.response.RegisterResponseDto
import com.uzuu.admin.data.remote.dto.response.TokenResponseDto

class AuthRemoteDataSource(
    private val api: AuthApi
) {
    suspend fun login(request: LoginRequestDto): BaseResponseDto<TokenResponseDto> {
        return api.login(request)
    }

    suspend fun register(request: RegisterRequestDto): BaseResponseDto<RegisterResponseDto> {
        return api.register(request)
    }

    suspend fun forgotPassword(request: ForgotPasswordRequestDto): BaseResponseDto<ForgotPasswordResponseDto> {
        return api.forgotPassword(request)
    }

    suspend fun verifyOtp(request: VerifyOtpRequestDto): BaseResponseDto<ForgotPasswordResponseDto> {
        return api.verifyOtp(request)
    }

    suspend fun resendOtp(request: ForgotPasswordRequestDto): BaseResponseDto<ForgotPasswordResponseDto> {
        return api.resendOtp(request)
    }
}