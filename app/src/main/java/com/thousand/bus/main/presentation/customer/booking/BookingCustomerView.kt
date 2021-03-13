package com.thousand.bus.main.presentation.customer.booking

import com.thousand.bus.entity.BusSeat
import com.thousand.bus.entity.Place
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseMvpView


interface BookingCustomerView : BaseMvpView {

    fun showBusSeatData(dataList: List<BusSeat>, typeBus: Int)

    fun showTotalPrice(price: Int)

//    fun showTravelInfo(travel: Travel)

    fun showSleepingSalon(show: Boolean)

    fun openPrePaymentCustomerFragment(place: Place)

}