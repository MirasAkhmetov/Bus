package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class FeedbackListQuery(
    var page: Int = 1
) : Parcelable
