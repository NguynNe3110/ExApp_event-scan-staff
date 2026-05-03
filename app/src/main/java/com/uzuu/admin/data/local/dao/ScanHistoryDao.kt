package com.uzuu.admin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uzuu.admin.data.local.entity.ScanHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanHistoryDao {
    
    @Insert
    suspend fun insertScanHistory(scanHistory: ScanHistoryEntity): Long
    
    @Query("SELECT * FROM scan_history ORDER BY checkInTime DESC")
    fun getAllScanHistory(): Flow<List<ScanHistoryEntity>>
    
    @Query("SELECT * FROM scan_history WHERE ticketCode = :ticketCode ORDER BY checkInTime DESC LIMIT 1")
    suspend fun getLastScanByTicketCode(ticketCode: String): ScanHistoryEntity?
    
    @Query("SELECT * FROM scan_history ORDER BY checkInTime DESC LIMIT :limit")
    fun getRecentScans(limit: Int): Flow<List<ScanHistoryEntity>>
    
    @Query("DELETE FROM scan_history WHERE checkInTime < :timestamp")
    suspend fun deleteOldScans(timestamp: Long): Int
    
    @Query("DELETE FROM scan_history")
    suspend fun clearAll(): Int
    
    @Query("SELECT COUNT(*) FROM scan_history")
    suspend fun getScanCount(): Int
}
