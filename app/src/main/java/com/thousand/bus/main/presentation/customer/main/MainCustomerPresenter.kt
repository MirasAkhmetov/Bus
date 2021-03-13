package com.thousand.bus.main.presentation.customer.main

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.DriverInteractor
import com.thousand.bus.main.data.interactor.ListInteractor

@InjectViewState
class MainCustomerPresenter(
    private val driverInteractor: DriverInteractor,
    private val listInteractor: ListInteractor
): BasePresenter<MainCustomerView>(){

    private var contact: String? = null

    fun onFirstInit(type: String? = null){
        LocalStorage.getUser()?.let { viewState?.showUserInfo(it) }
        when(type){
            "reservation" -> viewState?.openMyTicketFragment()
            "place_take" -> viewState?.openMyTicketFragment()
            else -> viewState?.openHomeCustomerFragment()
        }
        getSettings()
    }

    fun becomeDriverBtnClicked(){
        viewState?.showProgressBar(true)
        if (LocalStorage.getUser()?.confirmation == AppConstants.CONFIRMATION_CONFIRM ||
                LocalStorage.getUser()?.confirmation == AppConstants.CONFIRMATION_WAITING){
            driverInteractor.becomeDriver()
                .subscribe(
                    {
                        viewState?.showProgressBar(false)
                        viewState?.openMainDriverFragment()
                    },
                    {
                        it.printStackTrace()
                    }
                ).connect()
        }else{
            viewState?.openDriverConfirmFragment(true)
        }
    }

    private fun getSettings(){
        listInteractor.getTermsAndPolicy()
            .subscribe(
                {
                    contact = it.contact
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onContactItemClicked(){
        contact?.let { viewState?.openPhoneActionView(it) }
    }

}