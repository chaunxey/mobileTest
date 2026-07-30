package com.aisenz.moblietest.booking

import com.aisenz.moblietest.BookingApp
import com.aisenz.moblietest.common.readAssetsFile
import com.google.gson.Gson
import java.net.ConnectException
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class BookingMockingService private constructor() {

    companion object {
        val INSTANCE by lazy { BookingMockingService() }
    }

    //模拟请求数据
    suspend fun mockingBooking(): Result<BookingResponse?> {
        // 模拟网络延迟
        kotlinx.coroutines.delay(1000.milliseconds)
        return try {
            // 模拟接口报错
            if (Random.nextFloat() < 0.2f) {
                throw ConnectException("Network Error")
            }
            //读取assets下的JSON文件
            val jsonString = BookingApp.appContext.readAssetsFile("booking.json")
            val booking = Gson().fromJson(jsonString, BookingResponse::class.java)
            //假设30S后数据过期
            val futureExpiryTime = (System.currentTimeMillis() / 1000) + 30
            val newBooking = booking.copy(expiryTime = futureExpiryTime.toString())
            Result.success(newBooking)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}