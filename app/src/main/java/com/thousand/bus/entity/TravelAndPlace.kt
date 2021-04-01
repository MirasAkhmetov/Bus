package com.thousand.bus.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TravelAndPlace(
    @SerializedName("travel")
    val travel: Travel? = null,
    @SerializedName("places")
    val places: List<Places>? = null
)