package com.thousand.bus.main.presentation.driver.carlist

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.Car
import com.thousand.bus.entity.CreateTravel
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.DriverInteractor
import com.thousand.bus.main.presentation.driver.home.HomeDriverPresenter

@InjectViewState
class CarListDriverPresenter (
    private val driverInteractor: DriverInteractor
): BasePresenter<CarListDriverView>(){

    fun onFirstInit(){
        getCarList()
    }

    private fun getCarList(){
        viewState?.showProgressBar(true)
        driverInteractor.getCarList()
            .subscribe(
                {

                    viewState?.showCarListData(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onCarListItemSelected(car: Car, cardType:Int){
        if (car.is_confirmed==0){
            return
        }

        car.let {
            if (cardType == 1) viewState?.openHomeDriverFragment(carId = it.id!!)
            else if (cardType == 3) viewState.openPassengerFragment(carId = it.id!!)
            else viewState.openUpcomingDriverFragment(upcomingId = it.id!!)
        }
    }




}