package com.thousand.bus.main.presentation.auth.sign_in

import com.thousand.bus.global.base.BaseMvpView


interface SignInView : BaseMvpView {

    fun openSignUpWelcomeFragment()

    fun openMainCustomerFragment()

    fun openCustomerConfirmFragment()

    fun openDriverConfirmFragment()

    fun openRestorePhoneFragment()

}