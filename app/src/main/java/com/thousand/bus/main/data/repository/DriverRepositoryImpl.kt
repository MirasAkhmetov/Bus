package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.*
import com.thousand.bus.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DriverRepositoryImpl(
    private val serverService: ServerService
): DriverRepository{

    override fun createTravel(createTravel: CreateTravel): Completable =
        serverService.createTravel(createTravel)

    override fun getUpcomingTravels(id:Int): Single<List<Travel>> =
        serverService.getUpcomingTravels(id = id)

    override fun getTravelHistory(): Single<List<Travel>> =
        serverService.getTravelHistory()

    override fun getPassengers(id:Int?): Single<List<Place>> =
        serverService.getPassengers(id)

    override fun getCarList(): Single<List<Car>> =
        serverService.getCarList()

    override fun addBus(
        params: Map<String, RequestBody>,
        passportImage: MultipartBody.Part?,
        passportImageBack: MultipartBody.Part?,
        carImage: MultipartBody.Part?,
        carImageSecond: MultipartBody.Part?,
        carImageThird: MultipartBody.Part?,
        carAvatar: MultipartBody.Part?,
        identityImage : MultipartBody.Part?,
        identityImageBack : MultipartBody.Part?
    ): Single<User> = serverService.addBus(params,  passportImage, passportImageBack, carImage, carImageSecond, carImageThird, carAvatar, identityImage, identityImageBack)


    override fun editPlace(travelPlaceId: Int?, status: String?, passengerId: Int?): Completable =
        serverService.editPlace(travelPlaceId, status, passengerId)

    override fun becomeDriver(): Completable =
        serverService.becomeDriver()

    override fun deleteUpcomingTravel(travelId: Int): Completable =
        serverService.deleteUpcomingTravel(travelId)
}