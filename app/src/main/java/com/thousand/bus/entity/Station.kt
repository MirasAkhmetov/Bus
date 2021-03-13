package com.thousand.bus.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Station(
    @SerializedName("city_id")
    val cityId: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("station_id")
    val station_id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("stations")
    val stations: String? = null
)