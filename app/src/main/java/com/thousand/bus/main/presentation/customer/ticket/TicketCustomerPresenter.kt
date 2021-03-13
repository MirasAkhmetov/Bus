package com.thousand.bus.main.presentation.customer.ticket

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.Ticket
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.CustomerInteractor
import com.thousand.bus.main.data.interactor.DriverInteractor

@InjectViewState
class TicketCustomerPresenter(
    private val customerInteractor: CustomerInteractor
): BasePresenter<TicketCustomerView>(){

    fun onFirstInit(){
        getMyTicket()
    }

    private fun getMyTicket(){
        viewState?.showProgressBar(true)
        customerInteractor.getMyTickets()
            .subscribe(
                {
                    viewState?.showTickets(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onTakeBackItemClicked(ticket: Ticket){
        viewState?.showProgressBar(true)
        ticket.id?.let {
            customerInteractor.takeBackTicket(it)
                .subscribe(
                    {
                        getMyTicket()
                    },
                    {
                        it.printStackTrace()
                        viewState?.showMessage(it.getErrorMessage())
                    }
                ).connect()
        }
    }

}