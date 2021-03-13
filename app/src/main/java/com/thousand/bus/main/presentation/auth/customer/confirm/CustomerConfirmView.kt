package com.thousand.bus.main.presentation.auth.customer.confirm

import android.graphics.Bitmap
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.BaseMvpView


interface CustomerConfirmView : BaseMvpView {

    fun showAvatarImage(bitmap: Bitmap)

    fun openListDialogFragment(dataList: ArrayList<ListItem>)

    fun showSelectedCity(title: String)

    fun openCustomerMainFragment()

}