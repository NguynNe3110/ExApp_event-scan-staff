package com.uzuu.admin.data.remote.dto.response

data class CartResponseDto(
    val items: List<CartItemDto> = emptyList(),
    val totalAmount: Double = 0.0
)

data class CartItemDto(
    val id: Long,
    val ticketTypeId: Long,
    val quantity: Int,
    val price: Double
)
