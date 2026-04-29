package com.uzuu.admin.data.remote.dto.response

import com.google.gson.annotations.SerializedName

// Server có thể trả về object chứa message hoặc string trực tiếp
data class RegisterResponseDto(
    @SerializedName("result") val result: String? = null,
    @SerializedName("message") val message: String? = null
) {
    // Helper để lấy message từ result hoặc message field
    val message: String get() = result ?: message ?: ""
}
