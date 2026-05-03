package com.uzuu.admin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ticketCode: String,
    val ticketName: String? = null,
    val guestName: String? = null,
    val eventName: String? = null,
    val checkInTime: Long, // timestamp
    val status: String = "SUCCESS", // SUCCESS, FAILED, etc.
    val message: String? = null
)
