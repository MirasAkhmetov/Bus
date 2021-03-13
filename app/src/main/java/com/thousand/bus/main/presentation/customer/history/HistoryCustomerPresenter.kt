package com.thousand.bus.main.presentation.customer.history

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.CustomerInteractor
import com.thousand.bus.main.data.interactor.DriverInteractor

@InjectViewState
class HistoryCustomerPresenter(
    private val customerInteractor: CustomerInteractor
): BasePresenter<HistoryCustomerView>(){

    fun onFirstInit(){
        getTravelHistory()
    }

    private fun getTravelHistory(){
        viewState?.showProgressBar(true)
        customerInteractor.getOrderHistories()
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