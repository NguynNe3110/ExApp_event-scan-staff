package com.uzuu.admin.core.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.admin.data.local.dao.ScanHistoryDao
import com.uzuu.admin.data.local.entity.ScanHistoryEntity

@Database(
    entities = [ScanHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanHistoryDao(): ScanHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "uzuu_admin_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
