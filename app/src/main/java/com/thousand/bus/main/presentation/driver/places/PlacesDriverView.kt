package com.thousand.bus.main.presentation.driver.places

import com.thousand.bus.entity.BusSeat
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseMvpView


interface PlacesDriverView : BaseMvpView {

    fun showBusSeatData(dataList: List<BusSeat>, typeBus:Int)

}