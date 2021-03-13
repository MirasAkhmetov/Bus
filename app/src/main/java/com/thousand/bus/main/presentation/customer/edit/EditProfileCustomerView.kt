package com.thousand.bus.main.presentation.customer.edit

import android.graphics.Bitmap
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface EditProfileCustomerView : BaseMvpView {

    fun showUserInfo(user: User)

    fun showAvatarImage(bitmap: Bitmap)

    fun closeThisFragment()

}