package com.thousand.bus.main.presentation.customer.pre_payment

import com.thousand.bus.entity.Place
import com.thousand.bus.global.base.BaseMvpView


interface PrePaymentCustomerView : BaseMvpView {

    fun showPlaceInfo(place: Place)

    fun showWhatsappInfo(info: String)

    fun showTimerInfo(minute: Int)

    fun openActionView(phone: String, place: String, stateNumber: String)

    fun openHomeCustomerFragment()

    fun openCallToDriver(phone: String)
}