package com.thousand.bus.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FromTo(
    @SerializedName("departure_time")
    var departureTime: String? = null,
    @SerializedName("destination_time")
    var destinationTime: String? = null
)