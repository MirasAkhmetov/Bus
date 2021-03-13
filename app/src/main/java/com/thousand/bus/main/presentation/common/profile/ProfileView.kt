package com.thousand.bus.main.presentation.common.profile

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileView : BaseMvpView {

    fun showUserInfo(user: User)

    fun openEditProfileCustomerFragment()

    fun openWebViewFragment(content: String)

    fun openSignInFragment()

}