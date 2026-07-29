package com.aisenz.moblietest.nav3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data object ShipBookingRoute : NavKey

@Serializable
data class WebScreenRoute(val url: String) : NavKey