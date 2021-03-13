package com.thousand.bus.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: User? = null
)