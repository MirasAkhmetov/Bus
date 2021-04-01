package com.thousand.bus.entity

import androidx.annotation.Keep

@Keep
data class PushToDriver(
    val phone : String? = null,
    val name : String? = null
)
