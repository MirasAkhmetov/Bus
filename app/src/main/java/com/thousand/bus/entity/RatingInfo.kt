package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class RatingInfo(
    var rating : Int? = null,
    var criterion1 : Int? = null,
    var criterion2 : Int? = null,
    var criterion3 : Int? = null
) : Parcelable
