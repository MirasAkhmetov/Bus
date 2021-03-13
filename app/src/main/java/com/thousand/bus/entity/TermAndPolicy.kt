package com.thousand.bus.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TermAndPolicy(
    @SerializedName("contact")
    val contact: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("privacy_policy")
    val privacyPolicy: String? = null,
    @SerializedName("terms_of_use")
    val termsOfUse: String? = null,
    @SerializedName("whatsapp")
    val whatsapp: String? = null
)