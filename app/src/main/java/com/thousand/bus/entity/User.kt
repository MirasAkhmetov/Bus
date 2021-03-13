package com.thousand.bus.entity


import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class User(
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("identity_image")
    var identityImage: String? = null,
    @SerializedName("identity_image_back")
    var identityImageBack: String? = null,
    @SerializedName("lang")
    var lang: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("bank_card")
    var phoneBank: String? = null,
    @SerializedName("card_fullname")
    var nameBank: String? = null,
    @SerializedName("passport_image")
    var passportImage: String? = null,
    @SerializedName("passport_image_back")
    var passportImageBack: String? = null,
    @SerializedName("car_image")
    var carImage: String? = null,
    @SerializedName("car_image1")
    var carImageSecond: String? = null,
    @SerializedName("car_image2")
    var carImageThird: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("push")
    var push: String? = null,
    @SerializedName("role")
    var role: String? = null,
    @SerializedName("sound")
    var sound: String? = null,
    @SerializedName("surname")
    var surname: String? = null,
    @SerializedName("confirmation")
    var confirmation: String? = null,
    @SerializedName("car")
    var car: Car? = null,
    var avatarBitmap: Bitmap? = null,
    var identityBitmap: Bitmap? = null,
    var identityBackBitmap: Bitmap? = null,
    var passportBitmap: Bitmap? = null,
    var passportBackBitmap: Bitmap? = null,
    var carBitmap: Bitmap? = null,
    var carSecondBitmap: Bitmap? = null,
    var carThirdBitmap: Bitmap? = null
): Parcelable