package com.thousand.bus.entity


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Car(
    @SerializedName("baggage")
    var baggage: Int? = null,
    @SerializedName("car_type_id")
    var carTypeId: Int? = null,
    @SerializedName("conditioner")
    var conditioner: Int? = null,
    @SerializedName("count_places")
    var countPlaces: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    var image1: String? = null,
    var image2: String? = null,
    var avatar : String? = null,
    @SerializedName("state_number")
    var stateNumber: String? = null,
    @SerializedName("tv")
    var tv: Int? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("is_confirmed")
    var is_confirmed: Int? = null,
    var rating: Double? = null,
    var phone: String? = null

): Parcelable