package com.thousand.bus.main.data.interactor

import android.graphics.Bitmap
import com.thousand.bus.entity.User
import com.thousand.bus.entity.UserResponse
import com.thousand.bus.global.system.SchedulersProvider
import com.thousand.bus.global.utils.MultipartHelper
import com.thousand.bus.main.data.repository.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody

class AuthInteractor(
    private val authRepository: AuthRepository,
    private val scheduler: SchedulersProvider
){

    fun registration(
        phone: String,
        password: String,
        role: String,
        deviceToken: String
    ): Completable =
        authRepository.registration(phone, password, role, deviceToken)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun auth(): Single<User> =
        authRepository.auth()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun signIn(phone: String, password: String, deviceToken: String): Single<UserResponse> =
        authRepository.signIn(phone, password, deviceToken)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun confirmation(
        params: Map<String, RequestBody>,
        avatarBitmap: Bitmap? = null,
        passportBitmap: Bitmap? = null,
        passportBackBitmap: Bitmap? = null,
        identityBitmap: Bitmap? = null,
        identityBackBitmap: Bitmap? = null,
        carImageBitmap: Bitmap? = null,
        carSecondBitmap: Bitmap? = null,
        carThirdBitmap: Bitmap? = null
    ): Single<User> =
        authRepository.confirmation(
            params,
            MultipartHelper.getBitmapDataFromPath("avatar", avatarBitmap),
            MultipartHelper.getBitmapDataFromPath("passport_image", passportBitmap),
            MultipartHelper.getBitmapDataFromPath("passport_image_back", passportBackBitmap),
            MultipartHelper.getBitmapDataFromPath("identity_image", identityBitmap),
            MultipartHelper.getBitmapDataFromPath("identity_image_back", identityBackBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image", carImageBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image1", carSecondBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image2", carThirdBitmap)
        )
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun editProfile(
        params: Map<String, RequestBody>? = null,
        avatarBitmap: Bitmap? = null
    ): Completable =
        authRepository.editProfile(
            params,
            MultipartHelper.getBitmapDataFromPath("avatar", avatarBitmap)
        )
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun roleDriver(
        params: Map<String, RequestBody>,
        avatarBitmap: Bitmap? = null,
        passportBitmap: Bitmap? = null,
        passportBackBitmap: Bitmap? = null,
        identityBitmap: Bitmap? = null,
        identityBackBitmap: Bitmap? = null,
        carImageBitmap: Bitmap? = null,
        carSecondBitmap: Bitmap? = null,
        carThirdBitmap: Bitmap? = null
    ): Completable =
        authRepository.roleDriver(
            params,
            MultipartHelper.getBitmapDataFromPath("avatar", avatarBitmap),
            MultipartHelper.getBitmapDataFromPath("passport_image", passportBitmap),
            MultipartHelper.getBitmapDataFromPath("passport_image_back", passportBackBitmap),
            MultipartHelper.getBitmapDataFromPath("identity_image", identityBitmap),
            MultipartHelper.getBitmapDataFromPath("identity_image_back", identityBackBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image", carImageBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image1", carSecondBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image2", carThirdBitmap)
        ).subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun rolePassenger() : Completable =
        authRepository.rolePassenger()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun smsConfirmation(
        phone: String,
        code: String
    ): Single<UserResponse> =
        authRepository.smsConfirmation(phone, code)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun sendSmsForPasswordRestore(
        phone: String
    ): Single<String> =
        authRepository.sendSmsForPasswordRestore(phone)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun restorePassword(
        phone: String,
        password: String,
        code: String
    ): Single<User> =
        authRepository.restorePassword(phone, password, code)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun logout( token: String): Completable =
        authRepository.logout(token)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
}