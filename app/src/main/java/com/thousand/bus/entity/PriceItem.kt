package com.thousand.bus.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PriceItem(
    @SerializedName("from")
    var from: Int? = null,
    @SerializedName("price")
    var price: Int? = 500,
    @SerializedName("to")
    var to: Int? = null
)