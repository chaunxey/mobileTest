package com.aisenz.moblietest.booking

import com.aisenz.moblietest.common.DatastoreHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class BookingDataManager private constructor() {

    companion object {
        val INSTANCE by lazy { BookingDataManager() }
    }

    private val service: BookingMockingService = BookingMockingService.INSTANCE

    private val datastore: DatastoreHelper = DatastoreHelper.INSTANCE

    fun mockingShipBookingData(): Flow<Result<BookingResponse?>> {
        return flow {
            val bookingCache = datastore.getBookingCacheFlow().firstOrNull()
            println("booking bookingCache>> bookingCache:$bookingCache")
            //读取缓存 booking 有缓存且在有效期内则先展示缓存数据
            if (bookingCache != null && isCacheValid(bookingCache.expiryTime)) {
                emit(Result.success(bookingCache))
            }
            try {
                val newBooking = service.mockingBooking().getOrThrow()
                println("booking mockingBooking>> newBooking:$newBooking")
                datastore.saveBookingCache(newBooking)
                emit(Result.success(newBooking))
            } catch (e: Exception) {
                //处理请求错误
                emit(Result.failure(e))
            }
        }
    }


    // 检查时效性
    fun isCacheValid(expiryTime: String?): Boolean {
        return try {
            val expiryTimestamp = expiryTime?.toLongOrNull() ?: 0
            // 将秒级时间戳转换为毫秒级进行比较
            val currentTimeMillis = System.currentTimeMillis() / 1000
            currentTimeMillis < expiryTimestamp
        } catch (e: Exception) {
            false // 解析失败
        }
    }

}