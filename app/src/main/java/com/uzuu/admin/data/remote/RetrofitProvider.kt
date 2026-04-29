package com.uzuu.admin.data.remote

import com.uzuu.admin.data.remote.api.AuthApi
import com.uzuu.admin.data.remote.api.CheckInApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    // TODO: Thay đổi BASE_URL thành địa chỉ server thật của bạn
    // Ví dụ: "http://192.168.1.100:8080/" hoặc "https://api.yourdomain.com/"
    // Lưu ý: Nếu dùng emulator Android, dùng 10.0.2.2 thay cho localhost
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val client: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(logger)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi       by lazy { retrofit.create(AuthApi::class.java) }
    val checkInApi: CheckInApi by lazy { retrofit.create(CheckInApi::class.java) }
}