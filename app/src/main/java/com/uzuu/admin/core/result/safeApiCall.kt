package com.uzuu.admin.core.result

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(block: suspend () -> T): ApiResult<T> {
    return try {
        val result = block()
        ApiResult.Success(result)
    } catch (e: IOException) {
        ApiResult.Error("Lỗi mạng — kiểm tra kết nối", e)
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        // Thử parse lỗi từ server nếu có thể
        val errorMessage = try {
            errorBody?.let { 
                // Nếu errorBody là JSON, có thể extract message từ đó
                it 
            } ?: "HTTP ${e.code()} ${e.message()}"
        } catch (ex: Exception) {
            "HTTP ${e.code()} ${e.message()}"
        }
        ApiResult.Error(errorMessage, e)
    } catch (e: Exception) {
        ApiResult.Error("Lỗi không xác định: ${e.message}", e)
    }
}