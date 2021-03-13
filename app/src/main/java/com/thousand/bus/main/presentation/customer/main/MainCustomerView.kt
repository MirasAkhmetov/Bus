package com.thousand.bus.main.presentation.customer.main

import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseMvpView


interface MainCustomerView : BaseMvpView {

    fun openHomeCustomerFragment()

    fun openHistoryCustomerFragment()

    fun openMyTicketFragment()

    fun openMainDriverFragment()

    fun openDriverConfirmFragment(isRoleChange: Boolean)

    fun showUserInfo(user: User)

    fun openProfileFragment()

    fun openPhoneActionView(phone: String)

}