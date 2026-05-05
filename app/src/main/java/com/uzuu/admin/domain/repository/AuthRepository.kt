package com.uzuu.admin.domain.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.model.ForgotPassword
import com.uzuu.admin.domain.model.Login

interface AuthRepository {
    suspend fun login(request: Login): ApiResult<String>  // returns token
    suspend fun forgotPassword(request: ForgotPassword): ApiResult<String>  // returns success message
    suspend fun verifyOtp(email: String, otp: String): ApiResult<String>  // returns success message
    suspend fun resendOtp(email: String): ApiResult<String>  // returns success message
}