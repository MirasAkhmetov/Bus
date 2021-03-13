package com.thousand.bus.entity

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CreateTravel(
    @SerializedName("departure_time")
    var departureTime: String? = null,
    @SerializedName("destination_time")
    var destinationTime: String? = null,
    @SerializedName("from_station_id")
    var fromStationId: Int? = null,
    @SerializedName("stations")
    var stations: MutableList<Int>? = mutableListOf(),
    @SerializedName("to_station_id")
    var toStationId: Int? = null,
    @SerializedName("from_city_id")
    var fromCityId: Int? = null,
    @SerializedName("to_city_id")
    var toCityId: Int? = null,
    @SerializedName("carId")
    var carId: Int? = null,
    @SerializedName("place_price")
    var placePrices: MutableList<PriceItem> = mutableListOf(),
    @SerializedName("times")
    var times: MutableList<FromTo> = mutableListOf(),
    @Expose(serialize = false, deserialize = false)
    var fromCity: String? = null,
    @Expose(serialize = false, deserialize = false)
    var toCity: String? = null,
    @Expose(serialize = false, deserialize = false)
    var fromStation: String? = null,
    @Expose(serialize = false, deserialize = false)
    var toStation: String? = null
)