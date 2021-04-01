package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.User
import com.thousand.bus.entity.UserResponse
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

interface AuthRepository {

    fun registration(
        phone: String,
        password: String,
        role: String,
        deviceToken: String
    ): Completable

    fun auth(): Single<User>

    fun signIn(
        phone: String,
        password: String,
        deviceToken: String
    ): Single<UserResponse>

    fun confirmation(
        params: Map<String, RequestBody>,
        avatar: MultipartBody.Part?,
        passportImage: MultipartBody.Part?,
        passportImageBack: MultipartBody.Part?,
        identityImage: MultipartBody.Part?,
        identityImageBack: MultipartBody.Part?,
        carImage: MultipartBody.Part?,
        carImageSecond: MultipartBody.Part?,
        carImageThird: MultipartBody.Part?,
        carAvatar: MultipartBody.Part?
    ): Single<User>

    fun editProfile(
        params: Map<String, RequestBody>?,
        avatar: MultipartBody.Part?
    ): Completable

    fun roleDriver(
        params: Map<String, RequestBody>,
        avatar: MultipartBody.Part?,
        passportImage: MultipartBody.Part?,
        passportImageBack: MultipartBody.Part?,
        identityImage: MultipartBody.Part?,
        identityImageBack: MultipartBody.Part?,
        carImage: MultipartBody.Part?,
        carImageSecond: MultipartBody.Part?,
        carImageThird: MultipartBody.Part?,
        carAvatar: MultipartBody.Part?
    ): Completable

    fun rolePassenger() : Completable

    fun smsConfirmation(
        phone: String,
        code: String
    ): Single<UserResponse>

    fun sendSmsForPasswordRestore(
        phone: String
    ): Single<String>

    fun restorePassword(
        phone: String,
        password: String,
        code: String
    ): Single<User>

    fun logout( token: String): Completable

    fun confirmThePlace(orderId : Int?): Single<String>

}