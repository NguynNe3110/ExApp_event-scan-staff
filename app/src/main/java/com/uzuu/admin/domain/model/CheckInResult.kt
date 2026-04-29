package com.uzuu.admin.domain.model

data class CheckInResult(
    val id: Long,
    val eventName: String,
    val ticketTypeName: String,
    val ticketCode: String,
    val status: String,   // VALID | USED | EXPIRED
    val usedAt: String?
)