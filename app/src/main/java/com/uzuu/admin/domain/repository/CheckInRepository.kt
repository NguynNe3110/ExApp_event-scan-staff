package com.uzuu.admin.domain.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.domain.model.CheckInResult

interface CheckInRepository {
    suspend fun checkIn(ticketCode: String): ApiResult<CheckInResult>
}