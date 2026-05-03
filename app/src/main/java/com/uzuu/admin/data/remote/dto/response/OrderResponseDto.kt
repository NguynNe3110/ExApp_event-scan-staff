package com.uzuu.admin.data.remote.dto.response

data class OrderResponseDto(
    val id: Long,
    val paymentUrl: String? = null,
    val paymentStatus: String? = null
)
