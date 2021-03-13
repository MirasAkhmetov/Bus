package com.thousand.bus.main.presentation.driver.passenger

import com.thousand.bus.entity.Place
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseMvpView


interface PassengerDriverView : BaseMvpView {

    fun showPassengerData(dataList: List<Place>)

    fun openCarListDriverFragment()
}