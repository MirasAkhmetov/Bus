package com.thousand.bus.main.presentation.auth.restore.phone

import com.thousand.bus.global.base.BaseMvpView


interface PhoneRestoreView : BaseMvpView {

    fun openRestorePasswordFragment(phone: String)

}