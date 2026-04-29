package com.uzuu.admin.domain.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.model.ForgotPassword
import com.uzuu.admin.domain.model.Login
import com.uzuu.admin.domain.model.Register

interface AuthRepository {
    suspend fun login(request: Login): ApiResult<String>  // returns token
    suspend fun register(request: Register): ApiResult<String>  // returns success message
    suspend fun forgotPassword(request: ForgotPassword): ApiResult<String>  // returns success message
}