package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.User
import com.thousand.bus.entity.UserResponse
import com.thousand.bus.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AuthRepositoryImpl(
    private val serverService: ServerService
): AuthRepository{

    override fun registration(phone: String, password: String, role: String, deviceToken: String): Completable =
        serverService.registration(phone, password, role, deviceToken)

    override fun auth(): Single<User> =
        serverService.auth()

    override fun signIn(phone: String, password: String, deviceToken: String): Single<UserResponse> =
        serverService.signIn(phone, password, deviceToken)

    override fun confirmation(
        params: Map<String, RequestBody>,
        avatar: MultipartBody.Part?,
        passportImage: MultipartBody.Part?,
        passportImageBack: MultipartBody.Part?,
        identityImage: MultipartBody.Part?,
        identityImageBack: MultipartBody.Part?,
        carImage: MultipartBody.Part?,
        carImageSecond: MultipartBody.Part?,
        carImageThird: MultipartBody.Part?
    ): Single<User> =
        serverService.confirmation(params, avatar, passportImage, passportImageBack, identityImage,identityImageBack, carImage, carImageSecond, carImageThird)

    override fun editProfile(params: Map<String, RequestBody>?, avatar: MultipartBody.Part?) =
        serverService.editProfile(params, avatar)

    override fun roleDriver(
        params: Map<String, RequestBody>,
        avatar: MultipartBody.Part?,
        passportImage: MultipartBody.Part?,
        passportImageBack: MultipartBody.Part?,
        identityImage: MultipartBody.Part?,
        identityImageBack: MultipartBody.Part?,
        carImage: MultipartBody.Part?,
        carImageSecond: MultipartBody.Part?,
        carImageThird: MultipartBody.Part?
    ): Completable =
        serverService.roleDriver(params, avatar, passportImage, passportImageBack, identityImage,identityImageBack, carImage, carImageSecond, carImageThird)

    override fun rolePassenger(): Completable =
        serverService.rolePassenger()

    override fun smsConfirmation(phone: String, code: String): Single<UserResponse> =
        serverService.smsConfirmation(phone, code)

    override fun sendSmsForPasswordRestore(phone: String): Single<String> =
        serverService.sendSmsForPasswordRestore(phone)

    override fun restorePassword(phone: String, password: String, code: String): Single<User> =
        serverService.restorePassword(phone, password, code)

    override fun logout(token: String): Completable =
        serverService.logout(token)

}