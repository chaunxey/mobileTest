package com.aisenz.moblietest.booking

import com.aisenz.moblietest.mvi.BaseMviIntent
import com.aisenz.moblietest.mvi.BaseMviSideEffect
import com.aisenz.moblietest.mvi.BaseMviUiState

data class BookingUiState(
    val pageStatus: PageStatus = PageStatus.Loading,
    val isRefreshing: Boolean = false,
    val bookingData: BookingResponse? = null
) : BaseMviUiState

sealed class BookingIntent : BaseMviIntent {
    data object LoadBooking : BookingIntent()
    data object RefreshBooking : BookingIntent()
}

sealed class BookingSideEffect : BaseMviSideEffect {
    data class ShowToast(val message: String) : BookingSideEffect()
}

enum class PageStatus {
    Loading,
    Success,
    Error
}

