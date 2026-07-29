package com.aisenz.moblietest

import android.app.Application
import android.content.Context

class BookingApp : Application() {

    companion object {
        // 全局静态ApplicationContext
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}