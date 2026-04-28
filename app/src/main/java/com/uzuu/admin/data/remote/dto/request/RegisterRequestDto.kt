package com.uzuu.admin.data.remote.dto.request

data class RegisterRequestDto(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val organizeId: Long? = null
)
