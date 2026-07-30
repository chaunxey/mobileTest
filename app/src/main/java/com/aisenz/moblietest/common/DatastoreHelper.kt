package com.aisenz.moblietest.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aisenz.moblietest.BookingApp
import com.aisenz.moblietest.booking.BookingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


// 全局扩展
private val Context.dataStore by preferencesDataStore(name = "booking_cache")

class DatastoreHelper private constructor() {
    companion object {
        val INSTANCE by lazy { DatastoreHelper() }
    }

    //使用appContext 由于 Context.dataStore
    private val appContext = BookingApp.appContext
    private val json = Json { ignoreUnknownKeys = true }
    private val BOOKING_KEY = stringPreferencesKey("booking")

    //保存本地持久化数据 booking
    suspend fun saveBookingCache(booking: BookingResponse?) {
        if (booking != null) {
            runCatching {
                appContext.dataStore.edit {
                    it[BOOKING_KEY] = Json.encodeToString(booking)
                }
            }
        }
    }

    //获取本地持久化数据 booking
    fun getBookingCacheFlow(): Flow<BookingResponse?> {
        return appContext.dataStore.data
            .map { preferences ->
                val jsonString = preferences[BOOKING_KEY]
                // 将 JSON 字符串转换回对象
                if (jsonString != null) {
                    json.decodeFromString(BookingResponse.serializer(), jsonString)
                } else {
                    null
                }
            }
    }
}