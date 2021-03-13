package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.CarType
import com.thousand.bus.entity.City
import com.thousand.bus.entity.Station
import com.thousand.bus.entity.TermAndPolicy
import io.reactivex.Single

interface ListRepository {

    fun getCarTypes(): Single<List<CarType>>

    fun getCities(): Single<List<City>>

    fun getStations(cityId: Int): Single<List<Station>>

    fun getStationsBetweenCity(
        fromCityId: Int,
        toCityId: Int
    ): Single<List<Station>>

    fun getTermsAndPolicy(): Single<TermAndPolicy>

}