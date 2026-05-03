package com.uzuu.admin.data.remote.dto.response

data class VoucherResponseDto(
    val id: Long,
    val code: String,
    val discountType: String? = null,
    val amount: Double? = null,
    val maxDiscount: Double? = null,
    val minOrderAmount: Double? = null,
    val eventId: Long? = null
)
