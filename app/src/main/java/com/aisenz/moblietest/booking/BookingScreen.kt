package com.aisenz.moblietest.booking

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BookingScreen(goWeb: (String) -> Unit = {}) {
    val viewModel = viewModel<BookingViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val state = rememberPullToRefreshState()
    LaunchedEffect(Unit) {
        viewModel.processIntent(BookingIntent.LoadBooking)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                viewModel.processIntent(BookingIntent.RefreshBooking)
            },
            state = state,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = uiState.isRefreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = state
                )
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            //网络请求失败展示
            if (uiState.pageStatus == PageStatus.Error) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Network Error!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600
                    )
                    Button(onClick = {
                        viewModel.processIntent(BookingIntent.LoadBooking)
                    }) {
                        Text(
                            "Try again",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W600
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.bookingData?.let { booking ->
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    "ShipReference:${booking.shipReference}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W600
                                )

                                Text(
                                    "CanIssueTicketChecking:${if (booking.canIssueTicketChecking == true) "Yes" else "No"}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W600
                                )

                                Text(
                                    "ShipToken:${booking.shipToken}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Text(
                                    "ExpiryTime:${booking.expiryTime}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Text(
                                    "Duration:${booking.duration}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W500
                                )
                            }
                        }
                    }
                    uiState.bookingData?.segments?.forEachIndexed { _, segment ->
                        if (segment != null) {
                            item { BookingItem(segment, goWeb) }
                        }
                    }
                }
            }
        }

    }
}


@Composable
fun BookingItem(item: Segment, goWeb: (String) -> Unit = {}) {
    val pair = item.originAndDestinationPair
    Column(
        modifier = Modifier
            .clickable(onClick = { println("booking segment = $item") })
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "ID:${item.id}",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "${pair?.origin?.code}( ${pair?.origin?.displayName})",
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.clickable(onClick = {
                    goWeb.invoke(pair?.origin?.url ?: "")
                })
            )
            Text(
                "${pair?.destination?.code}( ${pair?.destination?.displayName})",
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.clickable(onClick = {
                    goWeb.invoke(pair?.destination?.url ?: "")
                })
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "${pair?.originCity}",
                fontSize = 12.sp,
                fontWeight = FontWeight.W400
            )
            Text(
                "${pair?.destinationCity})",
                fontSize = 12.sp,
                fontWeight = FontWeight.W400
            )
        }
    }

}