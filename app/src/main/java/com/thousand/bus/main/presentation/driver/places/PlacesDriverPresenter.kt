package com.thousand.bus.main.presentation.driver.places

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.BusSeat
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.CustomerInteractor
import com.thousand.bus.main.data.interactor.DriverInteractor
import timber.log.Timber

@InjectViewState
class PlacesDriverPresenter(
    private val travelId: Int,
    private val customerInteractor: CustomerInteractor,
    private val driverInteractor: DriverInteractor
): BasePresenter<PlacesDriverView>(){

    private var busSeatDataList: List<BusSeat> = listOf()
    private var typeBus: Int? = null

    fun onFirstInit(carTypeId:Int){

        typeBus = LocalStorage.getUser()?.car?.carTypeId

        when(carTypeId){
            AppConstants.CAR_TYPE_MINIVAN -> busSeatDataList = BusSeat.getMinivanSeat().toList()
            AppConstants.CAR_TYPE_TAXI -> busSeatDataList = BusSeat.getTaxiSeat().toList()
            AppConstants.CAR_TYPE_ALPHARD -> busSeatDataList = BusSeat.getAlphardSeat().toList()
            AppConstants.CAR_TYPE_36 -> busSeatDataList = BusSeat.get36BusSeat().toList()
            AppConstants.CAR_TYPE_50 -> busSeatDataList = BusSeat.get50BusSeat().toList()
        }
        typeBus?.let { viewState?.showBusSeatData(busSeatDataList, it) }
        getTravel(travelId)
    }

    private fun getTravel(travelId: Int){
        viewState?.showProgressBar(true)
        customerInteractor.getTravel(travelId)
            .subscribe(
                {
                    typeBus = it.travel?.car?.carTypeId

                    it.places?.forEach { place ->
                        busSeatDataList.forEach { seat ->
                            if (place.number == seat.id){
                                Timber.i("PL=${place.number}==SS${seat.id}")
                                seat.price = place.price
                                seat.passengerId = place.passenger?.id
                                seat.placeId = place.id
                                when(place.status){
                                    AppConstants.STATUS_FREE -> seat.state = BusSeat.STATE_FREE
                                    AppConstants.STATUS_IN_PROCESS -> seat.state = BusSeat.STATE_IN_PROCESS
                                    AppConstants.STATUS_TAKE -> seat.state = BusSeat.STATE_NOT_FREE
                                }
                            }
                        }
                    }
                    typeBus?.let { it1 -> viewState?.showBusSeatData(busSeatDataList, it1) }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun busSeatBtnClicked(busSeat: BusSeat){
        if (busSeat.passengerId == null){
            when(busSeat.state){
                BusSeat.STATE_FREE ->{
                    editPlace(busSeat, AppConstants.STATUS_TAKE)
                }
                BusSeat.STATE_NOT_FREE ->{
                    editPlace(busSeat, AppConstants.STATUS_FREE)
                }
            }
        }
        else{

        }
    }

    private fun editPlace(busSeat: BusSeat, status: String){
        viewState?.showProgressBar(true)
        driverInteractor.editPlace(
            travelPlaceId = busSeat.placeId,
            status = status,
            passengerId = busSeat.passengerId
        ).subscribe(
            {
                when(busSeat.state){
                    BusSeat.STATE_FREE -> busSeat.state = BusSeat.STATE_NOT_FREE
                    BusSeat.STATE_NOT_FREE -> busSeat.state = BusSeat.STATE_FREE
                }
                typeBus?.let { viewState?.showBusSeatData(busSeatDataList, it) }
                viewState?.showProgressBar(false)
            },
            {
                it.printStackTrace()
                viewState?.showProgressBar(false)
            }
        ).connect()
    }
}