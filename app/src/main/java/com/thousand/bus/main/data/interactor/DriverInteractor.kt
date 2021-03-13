package com.thousand.bus.main.data.interactor

import android.graphics.Bitmap
import com.thousand.bus.entity.*
import com.thousand.bus.global.system.SchedulersProvider
import com.thousand.bus.global.utils.MultipartHelper
import com.thousand.bus.main.data.repository.DriverRepository
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody

class DriverInteractor(
    private val driverRepository: DriverRepository,
    private val schedulersProvider: SchedulersProvider
){

    fun createTravel(createTravel: CreateTravel): Completable =
        driverRepository.createTravel(createTravel)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getUpcomingTravels(id:Int): Single<List<Travel>> =
        driverRepository.getUpcomingTravels(id = id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTravelHistory(): Single<List<Travel>> =
        driverRepository.getTravelHistory()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getPassengers(id: Int?): Single<List<Place>> =
        driverRepository.getPassengers(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCarList(): Single<List<Car>> =
        driverRepository.getCarList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun addBus(
        params: Map<String, RequestBody>,
        passportBitmap: Bitmap? = null,
        passportBackBitmap: Bitmap? = null,
        carImageBitmap: Bitmap? = null,
        carSecondBitmap: Bitmap? = null,
        carThirdBitmap: Bitmap? = null
    ): Single<User> =
        driverRepository.addBus(
            params,
            MultipartHelper.getBitmapDataFromPath("passport_image", passportBitmap),
            MultipartHelper.getBitmapDataFromPath("passport_image_back", passportBackBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image", carImageBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image1", carSecondBitmap),
            MultipartHelper.getBitmapDataFromPath("car_image2", carThirdBitmap))
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun editPlace(
        travelPlaceId: Int?,
        status: String?,
        passengerId: Int?
    ): Completable =
        driverRepository.editPlace(travelPlaceId, status, passengerId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun becomeDriver(): Completable =
        driverRepository.becomeDriver()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun deleteUpcomingTravel(travelId: Int): Completable =
        driverRepository.deleteUpcomingTravel(travelId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

}