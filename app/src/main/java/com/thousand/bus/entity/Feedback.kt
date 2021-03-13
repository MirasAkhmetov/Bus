package com.thousand.bus.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Feedback(
    var feedbackList : FeedbackList? = null,
    var ratingInfo : RatingInfo? = null
) : Parcelable
