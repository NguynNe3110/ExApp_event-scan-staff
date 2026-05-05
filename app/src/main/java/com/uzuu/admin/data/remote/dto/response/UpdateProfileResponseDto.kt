package com.uzuu.admin.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponseDto(
    val id: Long? = null,
    val username: String? = null,
    val email: String? = null,
    val fullName: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val role: String? = null,
    @SerializedName(value = "organizerName", alternate = ["organizerFullName", "ownerName", "managerName", "supervisorName"])
    val organizerName: String? = null,
    val message: String? = null
)
