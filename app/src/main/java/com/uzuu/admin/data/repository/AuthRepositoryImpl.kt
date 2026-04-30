package com.uzuu.admin.data.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.core.result.safeApiCall
import com.uzuu.admin.data.remote.datasource.AuthRemoteDataSource
import com.uzuu.admin.data.remote.dto.request.ForgotPasswordRequestDto
import com.uzuu.admin.data.remote.dto.request.LoginRequestDto
import com.uzuu.admin.data.remote.dto.request.RegisterRequestDto
import com.uzuu.admin.data.remote.dto.request.VerifyOtpRequestDto
import com.uzuu.admin.domain.model.ForgotPassword
import com.uzuu.admin.domain.model.Login
import com.uzuu.admin.domain.model.Register
import com.uzuu.admin.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(request: Login): ApiResult<String> =
        safeApiCall {
            val response = remote.login(LoginRequestDto(request.username, request.password))
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                response.result?.token ?: throw Exception("Đăng nhập thất bại")
            } else {
                throw Exception(response.message ?: "Đăng nhập thất bại")
            }
        }

    override suspend fun register(request: Register): ApiResult<String> =
        safeApiCall {
            val response = remote.register(
                RegisterRequestDto(
                    username = request.username,
                    password = request.password,
                    email = request.email,
                    fullName = request.fullName,
                    phone = request.phone,
                    address = request.address,
                    role = request.role
                )
            )
            val resultMessage = response.result?.getMessage() ?: response.message
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                resultMessage ?: "Đăng ký thành công"
            } else {
                throw Exception(response.message ?: "Đăng ký thất bại")
            }
        }

    override suspend fun forgotPassword(request: ForgotPassword): ApiResult<String> =
        safeApiCall {
            val response = remote.forgotPassword(ForgotPasswordRequestDto(request.email))
            val resultMessage = response.result?.getMessage() ?: response.message
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                resultMessage ?: "Yêu cầu khôi phục mật khẩu đã được gửi"
            } else {
                throw Exception(response.message ?: "Gửi yêu cầu khôi phục mật khẩu thất bại")
            }
        }

    override suspend fun verifyOtp(email: String, otp: String): ApiResult<String> =
        safeApiCall {
            val response = remote.verifyOtp(VerifyOtpRequestDto(email, otp))
            val resultMessage = response.result?.getMessage() ?: response.message
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                resultMessage ?: "Xác thực OTP thành công"
            } else {
                throw Exception(response.message ?: "Xác thực OTP thất bại")
            }
        }

    override suspend fun resendOtp(email: String): ApiResult<String> =
        safeApiCall {
            val response = remote.resendOtp(ForgotPasswordRequestDto(email))
            val resultMessage = response.result?.getMessage() ?: response.message
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                resultMessage ?: "Mã OTP đã được gửi lại"
            } else {
                throw Exception(response.message ?: "Gửi lại OTP thất bại")
            }
        }
}
