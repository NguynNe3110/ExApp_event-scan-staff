package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.VoucherValidationRequestDto
import com.uzuu.admin.data.remote.dto.response.VoucherResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VoucherApi {

    @POST("vouchers")
    suspend fun createVoucher(
        @Body request: VoucherResponseDto
    ): BaseResponseDto<VoucherResponseDto>

    @GET("vouchers")
    suspend fun listVouchers(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponseDto<List<VoucherResponseDto>>

    @POST("vouchers/validate")
    suspend fun validateVoucher(
        @Body request: VoucherValidationRequestDto
    ): BaseResponseDto<Double>

    @GET("vouchers/event/{eventId}")
    suspend fun listEventVouchers(
        @Path("eventId") eventId: Long
    ): BaseResponseDto<List<VoucherResponseDto>>
}
