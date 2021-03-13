package com.thousand.bus.main.presentation.driver.upcoming_travel

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.DriverInteractor

@InjectViewState
class UpcomingTravelDriverPresenter(
    private val driverInteractor: DriverInteractor
): BasePresenter<UpcomingTravelDriverView>(){
    var carFlag = 1
    var flag = false
    var upcomindId:Int = 0
    fun onFirstInit(carFlag:Int, upcomindId:Int){
        this.carFlag = carFlag
        this.upcomindId = upcomindId
        openCarList()
    }

    private fun getUpcomingTravels(id:Int){
        viewState?.showProgressBar(true)
        driverInteractor.getUpcomingTravels(id)
            .subscribe(
                {
                    viewState?.showTravelData(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onTravelItemSelected(travel: Travel){
        travel.let { viewState?.openPlacesDriverFragment(it) }
    }

    fun onDeleteItemSelected(travel: Travel){
        viewState?.showProgressBar(true)
        travel.id?.let {
            driverInteractor.deleteUpcomingTravel(it)
                .subscribe(
                    {
                        getUpcomingTravels(this.upcomindId)
                    },
                    {
                        it.printStackTrace()
                        viewState?.showProgressBar(false)
                    }
                ).connect()
        }
    }
    @SuppressLint("CheckResult")
    fun openCarList():Boolean{
        driverInteractor.getCarList().subscribe({
            if (it.size>1 && this.carFlag == 0)
            {
                viewState?.openCarListDriverFragment()
                this.flag = false

            }

            else {
                if (it.size == 1) {
                    this.upcomindId = it[0].id!!
                    getUpcomingTravels(this.upcomindId)

                } else{
                    getUpcomingTravels(this.upcomindId)
                }
                this.flag = true
            }

        },
            {
                it.printStackTrace()
                viewState?.showProgressBar(false)
            })

        return this.flag

    }
}