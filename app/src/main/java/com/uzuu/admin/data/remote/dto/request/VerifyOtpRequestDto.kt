package com.uzuu.admin.data.remote.dto.request

data class VerifyOtpRequestDto(
    val email: String,
    val otp: String
)
