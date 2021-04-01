package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Places(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("price")
    var price: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("to")
    val to: TravelStation? = null,
    @SerializedName("from")
    val from: TravelStation? = null,
    @SerializedName("car")
    var car: Car? = null,
    @SerializedName("driver")
    val driver: User? = null,
    @SerializedName("passenger")
    val passenger: User? = null,
    @SerializedName("booking_time")
    val bookingTime: String? = null,
    var departureDate: String? = null,
    var destinationDate: String? = null,
    var fromCity: String? = null,
    var fromStation: String? = null,
    var toCity: String? = null,
    var toStation: String? = null,
    var freePlacesCount: Int? = null,
    var reservedPlaceId: String? = null,
    var busSeats: List<BusSeat>? = null,
    var travelId: Int? = null
): Parcelable
