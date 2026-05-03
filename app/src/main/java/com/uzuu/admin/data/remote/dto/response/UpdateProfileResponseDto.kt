package com.uzuu.admin.data.remote.dto.response

data class UpdateProfileResponseDto(
    val id: Long? = null,
    val username: String? = null,
    val email: String? = null,
    val fullName: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val role: String? = null,
    val message: String? = null
)
