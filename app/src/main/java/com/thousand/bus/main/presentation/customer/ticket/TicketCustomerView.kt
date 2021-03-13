package com.thousand.bus.main.presentation.customer.ticket

import com.thousand.bus.entity.Ticket
import com.thousand.bus.global.base.BaseMvpView


interface TicketCustomerView : BaseMvpView {

    fun showTickets(dataList: List<Ticket>)

}