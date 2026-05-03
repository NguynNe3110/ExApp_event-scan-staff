package com.uzuu.admin.data.local.datasource

import com.uzuu.admin.data.local.dao.ScanHistoryDao
import com.uzuu.admin.data.local.entity.ScanHistoryEntity
import kotlinx.coroutines.flow.Flow

class ScanHistoryLocalDataSource(
    private val dao: ScanHistoryDao
) {
    suspend fun insertScanHistory(scanHistory: ScanHistoryEntity): Long {
        return dao.insertScanHistory(scanHistory)
    }

    fun getAllScanHistory(): Flow<List<ScanHistoryEntity>> {
        return dao.getAllScanHistory()
    }

    suspend fun getLastScanByTicketCode(ticketCode: String): ScanHistoryEntity? {
        return dao.getLastScanByTicketCode(ticketCode)
    }

    fun getRecentScans(limit: Int): Flow<List<ScanHistoryEntity>> {
        return dao.getRecentScans(limit)
    }

    suspend fun deleteOldScans(timestamp: Long): Int {
        return dao.deleteOldScans(timestamp)
    }

    suspend fun clearAll(): Int {
        return dao.clearAll()
    }

    suspend fun getScanCount(): Int {
        return dao.getScanCount()
    }
}
