package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Review(
    val name: String? =null,
    val surname : String? = null,
    val avatar : String? = null,
    val text : String? = null,
    var rating : Float? = null,
    var criterion1 : Int? = null,
    var criterion2 : Int? = null,
    var criterion3 : Int? = null
): Parcelable
