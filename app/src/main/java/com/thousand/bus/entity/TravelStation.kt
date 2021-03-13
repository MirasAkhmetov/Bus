package com.thousand.bus.entity


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class TravelStation(
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("city_id")
    val cityId: Int? = null,
    @SerializedName("station")
    val station: String? = null,
    @SerializedName("station_id")
    val stationId: Int? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lng")
    val lng: Double? = null
): Parcelable