package com.thousand.bus.global.utils

import com.thousand.bus.global.extension.errorMessage
import com.thousand.bus.global.system.ResourceManager


class ErrorHandler(private val resourceManager: ResourceManager) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        messageListener(error.errorMessage(resourceManager))
    }
}