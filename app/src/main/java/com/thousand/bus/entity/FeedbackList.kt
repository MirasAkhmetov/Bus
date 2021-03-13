package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class FeedbackList(
    @SerializedName("current_page")
    val currentPage : Int? = null,
    @SerializedName("data")
    val data: List<Review>? = null,
    @SerializedName("first_page_url")
    val firstPageUrl : String? = null,
    val from : Int? = null,
    @SerializedName("last_page")
    val lastPage : Int? = null,
    val path : String? = null,
    @SerializedName("last_page_url")
    val lastPageUrl : String? = null,
    @SerializedName("next_page_url")
    val nextPageUrl : String? = null,
    @SerializedName("per_page")
    val perPage : Int? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl : String? = null,
    val to : Int? = null,
    val total : Int? = null


) : Parcelable
