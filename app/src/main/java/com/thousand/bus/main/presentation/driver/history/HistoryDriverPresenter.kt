package com.thousand.bus.main.presentation.driver.history

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.DriverInteractor

@InjectViewState
class HistoryDriverPresenter(
    private val driverInteractor: DriverInteractor
): BasePresenter<HistoryDriverView>(){

    fun onFirstInit(){
        getTravelHistory()
    }

    private fun getTravelHistory(){
        viewState?.showProgressBar(true)
        driverInteractor.getTravelHistory()
            .subscribe(
                {
                    viewState?.showHistoryData(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

}