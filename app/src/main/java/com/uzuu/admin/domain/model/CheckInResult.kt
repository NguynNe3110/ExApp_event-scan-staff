package com.uzuu.admin.domain.model

data class Login(val username: String, val password: String)

data class Register(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val organizeId: Long? = null
)

data class ForgotPassword(val email: String)

data class CheckInResult(
    val id: Long,
    val eventName: String,
    val ticketTypeName: String,
    val ticketCode: String,
    val status: String,   // VALID | USED | EXPIRED
    val usedAt: String?
)