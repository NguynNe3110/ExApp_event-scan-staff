package com.uzuu.admin.data.repository

import com.uzuu.admin.core.result.ApiResult
import com.uzuu.admin.core.result.safeApiCall
import com.uzuu.admin.data.remote.datasource.CheckInRemoteDataSource
import com.uzuu.admin.domain.model.CheckInResult
import com.uzuu.admin.domain.repository.CheckInRepository

class CheckInRepositoryImpl(
    private val remote: CheckInRemoteDataSource
) : CheckInRepository {

    override suspend fun checkIn(ticketCode: String): ApiResult<CheckInResult> =
        safeApiCall {
            val response = remote.checkIn(ticketCode)
            if (response.code == 200 || response.code == 0 || response.code == 1000) {
                val dto = response.result
                CheckInResult(
                    id             = dto.id,
                    eventName      = dto.eventName,
                    ticketTypeName = dto.ticketTypeName,
                    ticketCode     = dto.ticketCode,
                    status         = dto.status,
                    usedAt         = dto.usedAt
                )
            } else {
                throw Exception(response.message ?: "Check-in thất bại")
            }
        }
}