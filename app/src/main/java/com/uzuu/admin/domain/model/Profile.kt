package com.uzuu.admin.domain.model

data class Profile(
    val id: Long? = null,
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val phone: String = "",
    val address: String = "",
    val role: String = ""
)
