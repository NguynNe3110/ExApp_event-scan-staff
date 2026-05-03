package com.uzuu.admin.data.remote.api

import com.uzuu.admin.data.remote.dto.BaseResponseDto
import com.uzuu.admin.data.remote.dto.request.CartItemRequestDto
import com.uzuu.admin.data.remote.dto.response.CartResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApi {

    @POST("cart/add")
    suspend fun addItem(
        @Body request: CartItemRequestDto
    ): BaseResponseDto<CartResponseDto>

    @GET("cart")
    suspend fun getCart(): BaseResponseDto<CartResponseDto>

    @PUT("cart/items/{itemId}")
    suspend fun updateItemQuantity(
        @Path("itemId") itemId: Long,
        @Query("quantity") quantity: Int
    ): BaseResponseDto<CartResponseDto>

    @DELETE("cart/items/{itemId}")
    suspend fun removeItem(
        @Path("itemId") itemId: Long
    ): BaseResponseDto<CartResponseDto>

    @DELETE("cart/clear")
    suspend fun clearCart(): BaseResponseDto<Void>
}
