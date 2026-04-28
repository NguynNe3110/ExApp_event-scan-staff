package com.uzuu.admin.domain.model

data class Register(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val organizeId: Long = 1L
)
