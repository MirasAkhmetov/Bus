package com.thousand.bus.entity


import com.google.gson.annotations.SerializedName

data class OrderHistory(
    @SerializedName("car_state_number")
    val carStateNumber: String? = null,
    @SerializedName("car_type")
    val carType: String? = null,
    @SerializedName("departure_time")
    val departureTime: String? = null,
    @SerializedName("destination_time")
    val destinationTime: String? = null,
    @SerializedName("from_city")
    val fromCity: String? = null,
    @SerializedName("from_station")
    val fromStation: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("to_city")
    val toCity: String? = null,
    @SerializedName("to_station")
    val toStation: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null
)