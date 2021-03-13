package com.thousand.bus.main.presentation.driver.upcoming_travel

import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseMvpView


interface UpcomingTravelDriverView : BaseMvpView {

    fun showTravelData(dataList: List<Travel>)

    fun openPlacesDriverFragment(travel:Travel)

    fun openCarListDriverFragment()

}