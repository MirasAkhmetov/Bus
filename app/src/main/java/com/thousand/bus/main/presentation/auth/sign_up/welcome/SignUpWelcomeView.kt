package com.thousand.bus.main.presentation.auth.sign_up.welcome

import com.thousand.bus.global.base.BaseMvpView


interface SignUpWelcomeView : BaseMvpView {

    fun openSignUpSmsFragment(phone: String)

    fun showUserRoleData(dataList: ArrayList<String>)

    fun openWebViewFragment(policy: String)
}