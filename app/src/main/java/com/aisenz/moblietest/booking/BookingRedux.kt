package com.aisenz.moblietest.booking

import com.aisenz.moblietest.mvi.BaseMviIntent
import com.aisenz.moblietest.mvi.BaseMviSideEffect
import com.aisenz.moblietest.mvi.BaseMviUiState
//唯一数据源
data class BookingUiState(
    val pageStatus: PageStatus = PageStatus.Loading,
    val isRefreshing: Boolean = false,
    val bookingData: BookingResponse? = null
) : BaseMviUiState

//用户意图统一入口
sealed class BookingIntent : BaseMviIntent {
    data object LoadBooking : BookingIntent()
    data object RefreshBooking : BookingIntent()
}
//副作用
sealed class BookingSideEffect : BaseMviSideEffect

enum class PageStatus {
    Loading,
    Success,
    Error
}

