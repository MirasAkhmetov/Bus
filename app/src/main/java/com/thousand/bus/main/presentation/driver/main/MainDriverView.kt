package com.thousand.bus.main.presentation.driver.main

import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseMvpView


interface MainDriverView : BaseMvpView {

    fun openHomeDriverFragment(carId:Int)

    fun openCarListDriverFragment()

    fun openUpcomingTravelDriverFragment()

    fun openHistoryDriverFragment()

    fun openPassengerDriverFragment(carId: Int = 0)

    fun openMainCustomerFragment()

    fun showUserInfo(user: User)

    fun openProfileFragment()

    fun openPhoneActionView(phone: String)

    fun openAddBusFragment()

}