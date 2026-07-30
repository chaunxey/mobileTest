package com.aisenz.moblietest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.aisenz.moblietest.booking.BookingScreen
import com.aisenz.moblietest.nav3.ShipBookingRoute
import com.aisenz.moblietest.nav3.WebScreenRoute
import com.aisenz.moblietest.ui.theme.MoblieTestTheme
import com.aisenz.moblietest.web.WebContainerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoblieTestTheme {
                //设置默认路由ShipBookingRoute
                val navBackStack = rememberNavBackStack(ShipBookingRoute)
                Scaffold(
//                    contentWindowInsets = WindowInsets(0.dp),// 关闭所有自动内边距
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(innerPadding)
                    ) {
                        NavDisplay(
                            backStack = navBackStack,
                            onBack = { navBackStack.removeLastOrNull() },
                            entryProvider = entryProvider {
                                entry<ShipBookingRoute> {
                                    BookingScreen(goWeb = {
                                        if (it.isNotBlank()) {
                                            navBackStack.add(WebScreenRoute(it))
                                        }
                                    })
                                }
                                entry<WebScreenRoute> {
                                    WebContainerScreen(it.url)
                                }
                            })
                    }
                }
            }
        }
    }
}


