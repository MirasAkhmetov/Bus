package com.thousand.bus.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Travel(
    @SerializedName("departure_time")
    val departureTime: String? = null,
    @SerializedName("destination_time")
    val destinationTime: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("max_price")
    val maxPrice: Int? = null,
    @SerializedName("min_price")
    val minPrice: Int? = null,
    @SerializedName("from")
    val from: TravelStation? = null,
    @SerializedName("to")
    val to: TravelStation? = null,
    @SerializedName("car")
    val car: Car? = null,
    @SerializedName("count_free_places")
    val countFreePlaces: Int? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("stations")
    val stations: MutableList<Station>? = null
)