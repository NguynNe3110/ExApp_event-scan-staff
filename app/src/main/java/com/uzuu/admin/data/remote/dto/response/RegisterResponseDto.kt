package com.uzuu.admin.data.remote.dto.response

import com.google.gson.annotations.SerializedName

// Server trả về String trực tiếp thay vì object
data class RegisterResponseDto(
    @SerializedName("result") val result: String? = null
) {
    // Helper để lấy message từ result nếu có
    val message: String get() = result ?: ""
}
