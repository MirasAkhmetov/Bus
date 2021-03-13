package com.thousand.bus.main.presentation.driver.history

import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseMvpView


interface HistoryDriverView : BaseMvpView {

    fun showHistoryData(dataList: List<Travel>)

}