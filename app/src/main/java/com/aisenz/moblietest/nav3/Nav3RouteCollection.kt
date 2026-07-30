package com.aisenz.moblietest.nav3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

//ShipBookingRoute页路由
@Serializable
data object ShipBookingRoute : NavKey
//通用web路由
@Serializable
data class WebScreenRoute(val url: String) : NavKey