package com.uzuu.admin.data.remote.dto.request

data class UpdateProfileRequestDto(
    val fullName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null
)
