package com.thousand.bus.global.functional

import android.content.Context
import com.thousand.bus.global.extension.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}