package com.thousand.bus.main.data.interactor

import com.thousand.bus.entity.CarType
import com.thousand.bus.entity.City
import com.thousand.bus.entity.Station
import com.thousand.bus.entity.TermAndPolicy
import com.thousand.bus.global.system.SchedulersProvider
import com.thousand.bus.main.data.repository.ListRepository
import io.reactivex.Single

class ListInteractor(
    private val listRepository: ListRepository,
    private val schedulersProvider: SchedulersProvider
){

    fun getCarTypes(): Single<List<CarType>> =
        listRepository.getCarTypes()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCities(): Single<List<City>> =
        listRepository.getCities()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getStations(cityId: Int): Single<List<Station>> =
        listRepository.getStations(cityId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getStationsBetweenCity(
        fromCityId: Int,
        toCityId: Int
    ): Single<List<Station>> =
        listRepository.getStationsBetweenCity(fromCityId, toCityId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTermsAndPolicy(): Single<TermAndPolicy> =
        listRepository.getTermsAndPolicy()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

}