package com.aisenz.moblietest.booking

import androidx.lifecycle.viewModelScope
import com.aisenz.moblietest.mvi.BaseMviViewModel
import kotlinx.coroutines.launch

class BookingViewModel :
    BaseMviViewModel<BookingUiState, BookingIntent, BookingSideEffect>(BookingUiState()) {
    private val dataManager = BookingDataManager.INSTANCE
    override fun processIntent(intent: BookingIntent) {
        when (intent) {
            is BookingIntent.LoadBooking -> {
                mockShipBookingRequest()
            }

            is BookingIntent.RefreshBooking -> {
                mockShipBookingRequest(true)
            }
        }
    }

    private fun mockShipBookingRequest(isRefresh: Boolean = false) = viewModelScope.launch {
        if (isRefresh) updateUiState { copy(isRefreshing = true) }
        dataManager.mockingShipBookingData().collect { result ->
            result.fold(
                onSuccess = {
                    println("booking mockShipBookingRequest>> success:$it")
                    updateUiState {
                        copy(
                            bookingData = it,
                            pageStatus = PageStatus.Success,
                            isRefreshing = false
                        )
                    }
                },
                onFailure = {
                    //可以扩展请求错误处理
                    updateUiState { copy(pageStatus = PageStatus.Error, isRefreshing = false) }
                    println("booking mockShipBookingRequest>> onFailure:$it")
                },
            )
        }
    }
}
