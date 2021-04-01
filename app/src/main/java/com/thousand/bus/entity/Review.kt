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
    var rating : Double? = null,
    var criterion1 : Double? = null,
    var criterion2 : Double? = null,
    var criterion3 : Double? = null
): Parcelable
