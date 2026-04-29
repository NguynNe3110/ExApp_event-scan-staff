package com.uzuu.admin.data.remote

import com.uzuu.admin.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    // Danh sách các endpoint không cần authentication
    private val publicEndpoints = listOf(
        "/auth/login",
        "/auth/register",
        "/auth/forgot-password"
    )
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        
        // Chỉ thêm token nếu không phải public endpoint và token tồn tại
        val token = SessionManager.getToken()
        val needsAuth = publicEndpoints.none { path.contains(it) }
        
        val newRequest = if (token != null && needsAuth) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }
        return chain.proceed(newRequest)
    }
}