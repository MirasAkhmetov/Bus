package com.thousand.bus.main.presentation.customer.detail

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.CustomerInteractor

@InjectViewState
class OrderDetailCustomerPresenter(
    private val travelId: Int,
    private val customerInteractor: CustomerInteractor
): BasePresenter<OrderDetailCustomerView>(){

    fun onFirstInit(){
        getTravel(travelId)
    }

    private fun getTravel(travelId: Int){
        customerInteractor.getTravel(travelId)
            .subscribe(
                {

                    it.travel?.let { viewState?.showTravelInfo(it) }

                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

}