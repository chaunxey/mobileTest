package com.aisenz.moblietest.common

import android.content.Context


//获取assets文件 mock booking data
fun Context.readAssetsFile(fileName: String): String {
    return assets.open("mock/$fileName").bufferedReader().use { it.readText() }
}