package com.thousand.bus.main.presentation.customer.history

import com.thousand.bus.entity.OrderHistory
import com.thousand.bus.global.base.BaseMvpView


interface HistoryCustomerView : BaseMvpView {

    fun showHistoryData(dataList: List<OrderHistory>)

}