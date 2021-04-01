package com.thousand.bus.main.presentation.customer.search_result

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.Travel
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.presentation.Paginator
import com.thousand.bus.main.data.interactor.CustomerInteractor

@InjectViewState
class SearchResultCustomerPresenter(
    private val travelListQuery: TravelListQuery,
    private val customerInteractor: CustomerInteractor
) : BasePresenter<SearchResultCustomerView>() {

    fun onFirstInit() {
        viewState?.showTravelInfo(travelListQuery)

        loadData()
        setCount(isBus = 2)
        setCount(isBus = 0)
        setCount(isBus = 1)

    }

    fun onAlphardInit() {
        viewState?.showTravelInfo(travelListQuery)
        loadDataAlphard()
    }

    fun onTaxiInit() {
        viewState?.showTravelInfo(travelListQuery)
        loadDataTaxi()
    }

    private fun setCount(isBus: Int) {
        this.travelListQuery.isBus = isBus
        customerInteractor.getTravelCount(travelListQuery)
            .subscribe(
                {
                    viewState.setCountTab(it?.count!!, isBus)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onOrderItemSelected(travel: Travel) {
        travel.id?.let { travel.car?.id?.let { it1-> travel.car?.stateNumber?.let { it2 ->
            viewState?.openBookingCustomerFragment(it,
                it1, it2
            )
        } } }
    }


    fun onOpenImageDialog(travel: Travel) {
        travel.id?.let { viewState?.openImageDialog(travel) }
    }


    private val paginator = Paginator(
        {
            travelListQuery.page = it
            customerInteractor.getTravelList(travelListQuery)
        },
        object : Paginator.ViewController<Travel> {
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {

            }

            override fun showEmptyView(show: Boolean) {
                viewState?.showTravelData(listOf())
            }

            override fun showData(show: Boolean, data: List<Travel>) {
                viewState?.showTravelData(data)
            }

            override fun showErrorMessage(error: Throwable) {
                viewState?.showMessage(error.getErrorMessage())
            }

            override fun showRefreshProgress(show: Boolean) {

            }

            override fun showPageProgress(show: Boolean) {

            }
        }
    )

    fun loadData() {
        travelListQuery.isBus = 1
        paginator.refresh()
    }

    fun loadDataAlphard() {
        travelListQuery.isBus = 0
        paginator.refresh()
    }

    fun loadDataTaxi() {
        travelListQuery.isBus = 2
        paginator.refresh()
    }

    fun loadDataByFilter() {
        travelListQuery.filter = "rating"
        paginator.refresh()
    }

    fun loadDataNextPage() = paginator.loadNewPage()

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

}