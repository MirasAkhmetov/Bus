package com.thousand.bus.main.presentation.auth.sign_up.sms

import com.thousand.bus.global.base.BaseMvpView


interface SignUpSmsView : BaseMvpView {

    fun openCustomerConfirmFragment()
    fun openDriverConfirmFragment()

}