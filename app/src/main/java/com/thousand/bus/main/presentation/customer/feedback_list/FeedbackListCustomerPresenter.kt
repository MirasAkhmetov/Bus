package com.thousand.bus.main.presentation.customer.feedback_list

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.*
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.presentation.Paginator
import com.thousand.bus.main.data.interactor.CustomerInteractor

@InjectViewState
class FeedbackListCustomerPresenter(
    private val carId: Int,
    private val feedbackListQuery: FeedbackListQuery,
    private val customerInteractor: CustomerInteractor
) : BasePresenter<FeedbackListCustomerView>() {


    fun onFirstInit() {
        getFeedbackList(carId)
        loadData()
    }

    private fun getFeedbackList(carId: Int) {
        viewState?.showProgressBar(true)
        customerInteractor.getFeedbackList(carId)
            .subscribe(
                {
                    viewState?.showFeedbackList(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    private val paginator = Paginator(
        {
            feedbackListQuery.page = it
             customerInteractor.getFeedbackListPagination(feedbackListQuery, carId)
    },
        object : Paginator.ViewController<Review> {
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {

            }

            override fun showEmptyView(show: Boolean) {
                viewState?.showFeedbackData(listOf())
            }

            override fun showData(show: Boolean, data: List<Review>) {
                viewState?.showFeedbackData(data)
            }

            override fun showErrorMessage(error: Throwable) {
                viewState?.showMessage(error.getErrorMessage())
            }

            override fun showRefreshProgress(show: Boolean) {

            }

            override fun showPageProgress(show: Boolean) {

            }
        })

    fun loadDataNextPage() = paginator.loadNewPage()

    fun loadData(){
        paginator.refresh()
    }


}