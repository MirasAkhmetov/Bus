package com.thousand.bus.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TravelResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("limit")
    val limit: String? = null,
    @SerializedName("offset")
    val offset: Int? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("pages")
    val pages: Int? = null,
    @SerializedName("data")
    val data: List<Travel>? = null
)