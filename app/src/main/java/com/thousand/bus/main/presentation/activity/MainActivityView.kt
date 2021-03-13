package com.thousand.bus.main.presentation.activity

import com.thousand.bus.global.base.BaseMvpView

interface MainActivityView : BaseMvpView {

    fun openSignInFragment()

    fun openMainCustomerFragment()

    fun openMainDriverFragment()

    fun openCustomerConfirmFragment()

    fun openDriverConfirmFragment()

    fun openWelcomeFragment()

}