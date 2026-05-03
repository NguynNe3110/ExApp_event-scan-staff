package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.CheckoutSelectedRequestDto
import com.uzuu.admin.data.remote.dto.response.OrderResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookingApi {

    @POST("bookings/checkout")
    suspend fun checkout(
        @Query("paymentMethod") paymentMethod: String,
        @Query("voucherCode") voucherCode: String? = null
    ): BaseResponseDto<OrderResponseDto>

    @POST("bookings/checkout-selected")
    suspend fun checkoutSelected(
        @Body itemIds: CheckoutSelectedRequestDto,
        @Query("paymentMethod") paymentMethod: String,
        @Query("voucherCode") voucherCode: String? = null
    ): BaseResponseDto<OrderResponseDto>

    @GET("bookings")
    suspend fun listBookings(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponseDto<List<OrderResponseDto>>
}
