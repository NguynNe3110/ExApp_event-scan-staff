package com.uzuu.admin.domain.model

data class ScanHistory(
    val id: Long = 0,
    val ticketCode: String = "",
    val ticketName: String? = null,
    val guestName: String? = null,
    val eventName: String? = null,
    val checkInTime: Long = 0,
    val status: String = "SUCCESS",
    val message: String? = null
)
