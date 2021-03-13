package com.thousand.bus.main.presentation.customer.feedback_list

import com.thousand.bus.entity.Review
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.CustomerInteractor

class FeedbackListCustomerPresenter(
    private val customerInteractor: CustomerInteractor
    ) : BasePresenter<FeedbackListCustomerView>() {

    private var review : List<Review> = listOf()
    private var carId = 0

    fun onFirstInit(){
        getFeedbackList(carId)
    }

    private fun getFeedbackList(carId : Int?){
        viewState?.showProgressBar(true)
        customerInteractor.getFeedbackList(carId)
            .subscribe(
                {
                    viewState?.showFeedbackList(review ,it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }
}