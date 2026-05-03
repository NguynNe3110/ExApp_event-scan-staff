package com.uzuu.admin.core.di

import android.content.Context
import com.uzuu.admin.data.local.datasource.ScanHistoryLocalDataSource
import com.uzuu.admin.data.remote.RetrofitProvider
import com.uzuu.admin.data.remote.datasource.AuthRemoteDataSource
import com.uzuu.admin.data.remote.datasource.CheckInRemoteDataSource
import com.uzuu.admin.data.remote.datasource.ProfileRemoteDataSource
import com.uzuu.admin.data.repository.AuthRepositoryImpl
import com.uzuu.admin.data.repository.CheckInRepositoryImpl
import com.uzuu.admin.data.repository.ProfileRepositoryImpl
import com.uzuu.admin.data.session.SessionManager
import com.uzuu.admin.domain.repository.AuthRepository
import com.uzuu.admin.domain.repository.CheckInRepository
import com.uzuu.admin.domain.repository.ProfileRepository

class AppContainer(context: Context) {

    init {
        SessionManager.init(context)
    }

    private val database = AppDatabase.getInstance(context)

    val authRepo: AuthRepository by lazy {
        AuthRepositoryImpl(
            AuthRemoteDataSource(RetrofitProvider.authApi)
        )
    }

    val checkInRepo: CheckInRepository by lazy {
        CheckInRepositoryImpl(
            CheckInRemoteDataSource(RetrofitProvider.checkInApi)
        )
    }

    val profileRepo: ProfileRepository by lazy {
        ProfileRepositoryImpl(
            ProfileRemoteDataSource(RetrofitProvider.profileApi),
            ScanHistoryLocalDataSource(database.scanHistoryDao())
        )
    }
}