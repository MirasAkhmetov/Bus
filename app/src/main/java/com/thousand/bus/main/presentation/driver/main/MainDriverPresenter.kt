package com.thousand.bus.main.presentation.driver.main

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import com.thousand.bus.main.data.interactor.CustomerInteractor
import com.thousand.bus.main.data.interactor.DriverInteractor
import com.thousand.bus.main.data.interactor.ListInteractor

@InjectViewState
class MainDriverPresenter(
    private val customerInteractor: CustomerInteractor,
    private val authInteractor: AuthInteractor,
    private val driverInteractor: DriverInteractor,
    private val listInteractor: ListInteractor
) : BasePresenter<MainDriverView>() {

    private var contact: String? = null

    fun onFirstInit(type: String? = null) {
        LocalStorage.getUser()?.let { viewState?.showUserInfo(it) }
        when (type) {
            "driver_confirmation" -> {
                viewState?.openPassengerDriverFragment()
            }
            else -> {
                openCarList()
            }
        }
        getSettings()
    }

    fun becomePassengerBtnClicked() {
        viewState?.showProgressBar(true)
        customerInteractor.becomePassenger()
            .subscribe(
                {
                    viewState?.showProgressBar(false)
                    viewState?.openMainCustomerFragment()
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onHomeBtnClicked() {
        viewState?.showProgressBar(true)
        authInteractor.auth()
            .subscribe(
                {
                    LocalStorage.setUser(it)
                    openCarList()
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    @SuppressLint("CheckResult")
    fun openCarList() {
        driverInteractor.getCarList().subscribe({
            if (it.size > 1)
                viewState?.openCarListDriverFragment()
            else viewState?.openHomeDriverFragment(it[0].id!!, it[0].carTypeId!!)
        },
            {
                it.printStackTrace()
                viewState?.showProgressBar(false)
            })

    }

    private fun getSettings() {
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

    fun onContactItemClicked() {
        contact?.let { viewState?.openPhoneActionView(it) }
    }

}