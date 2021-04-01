package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

interface DriverRepository{

    fun createTravel(createTravel: CreateTravel): Completable

    fun getUpcomingTravels(id:Int): Single<List<Travel>>

    fun getTravelHistory(): Single<List<Travel>>

    fun getPassengers(id:Int?): Single<List<Place>>

    fun getCarList(): Single<List<Car>>

    fun addBus(
        params: Map<String, RequestBody>,
        passportImage: MultipartBody.Part?,
        passportImageBack: MultipartBody.Part?,
        carImage: MultipartBody.Part?,
        carImageSecond: MultipartBody.Part?,
        carImageThird: MultipartBody.Part?,
        carAvatar: MultipartBody.Part?,
        identityImage: MultipartBody.Part?,
        identityImageBack: MultipartBody.Part?
    ): Single<User>


    fun editPlace(
        travelPlaceId: Int?,
        status: String?,
        passengerId: Int?
    ): Completable

    fun becomeDriver(): Completable

    fun deleteUpcomingTravel(travelId: Int): Completable

}