package com.thousand.bus.main.presentation.customer.pre_payment

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.Place
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.CustomerInteractor
import com.thousand.bus.main.data.interactor.ListInteractor

@InjectViewState
class PrePaymentCustomerPresenter(
    private val place: Place?,
    private val listInteractor: ListInteractor,
    private val customerInteractor: CustomerInteractor
): BasePresenter<PrePaymentCustomerView>(){

    private var whatsappPhone : String? = null

    fun onFirstInit(){
        place?.let { viewState?.showPlaceInfo(it) }
        when {
            LocalStorage.getOrderIds() == LocalStorage.PREF_NO_VAL -> {
                LocalStorage.setCurrentTime(15)
            }
            LocalStorage.getOrderIds() == place?.reservedPlaceId -> {

            }
            else -> {
                LocalStorage.setCurrentTime(15)
            }
        }
        viewState?.showTimerInfo(LocalStorage.getCurrentTime())
        getSettings()
    }

    private fun getSettings(){
        listInteractor.getTermsAndPolicy()
            .subscribe(
                {
                    whatsappPhone = it.whatsapp
                    it.whatsapp?.let { viewState?.showWhatsappInfo(it) }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onWhatsappBtnClicked(){
        val params: MutableMap<String, String> = mutableMapOf()
        place?.busSeats?.forEachIndexed { index, busSeat ->
            params["places[${index}]"] = busSeat.id?.toString() ?: ""
        }
        viewState?.showProgressBar(true)
        customerInteractor.placeReservation(
            travelId = place?.travelId,
            places = params
        ).subscribe(
            {
                whatsappPhone?.let { phone ->
                    place?.reservedPlaceId?.let { places ->
                        viewState?.openActionView(phone, places, place.car?.stateNumber ?: "")
                    }
                }
                Handler().postDelayed(
                    {
                        viewState?.openHomeCustomerFragment()
                        viewState?.showProgressBar(false)
                    },
                    2000
                )
            },
            {
                it.printStackTrace()
                viewState?.showMessage(it.getErrorMessage())
                viewState?.showProgressBar(false)
            }
        ).connect()
    }
}