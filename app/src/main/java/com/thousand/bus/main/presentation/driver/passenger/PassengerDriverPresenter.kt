package com.thousand.bus.main.presentation.driver.passenger

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.DriverInteractor

@InjectViewState
class PassengerDriverPresenter(
    private val driverInteractor: DriverInteractor
): BasePresenter<PassengerDriverView>(){

    var carId:Int ?= null
    fun onFirstInit(){
        openCarList()
        getPassengers()
    }

    fun setCarID(carId:Int) {

        if (carId != 0){
            this.carId = carId
        }
    }

    private fun getPassengers(){
        viewState?.showProgressBar(true)

        driverInteractor.getPassengers(carId)
            .subscribe(
                {
                    viewState?.showPassengerData(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    @SuppressLint("CheckResult")
    fun openCarList(){
        driverInteractor.getCarList().subscribe({
            if (it.size>1 && this.carId == null)
            {
                viewState?.openCarListDriverFragment()
            }
            else {}


        },
            {
                it.printStackTrace()
                viewState?.showProgressBar(false)
            })


    }

}