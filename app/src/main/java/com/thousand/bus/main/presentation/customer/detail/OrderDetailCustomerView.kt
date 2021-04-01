package com.thousand.bus.main.presentation.customer.detail

import com.thousand.bus.entity.Station
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseMvpView


interface OrderDetailCustomerView : BaseMvpView {

    fun showTravelInfo(travel: Travel)


}