package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.CarType
import com.thousand.bus.entity.City
import com.thousand.bus.entity.Station
import com.thousand.bus.entity.TermAndPolicy
import com.thousand.bus.global.service.ServerService
import io.reactivex.Single

class ListRepositoryImpl(
    private val service: ServerService
): ListRepository{

    override fun getCarTypes(): Single<List<CarType>> =
        service.getCarType()

    override fun getCities(): Single<List<City>> =
        service.getCities()

    override fun getStations(cityId: Int): Single<List<Station>> =
        service.getStations(cityId)

    override fun getStationsBetweenCity(fromCityId: Int, toCityId: Int): Single<List<Station>> =
        service.getStationsBetweenCity(fromCityId, toCityId)

    override fun getTermsAndPolicy(): Single<TermAndPolicy> =
        service.getTermsAndPolicy()

}