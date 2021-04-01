package com.thousand.bus.main.presentation.auth.driver.confirm

import android.graphics.Bitmap
import android.net.Uri
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.BaseMvpView


interface DriverConfirmView : BaseMvpView {

    fun openDriverConfirmWaitFragment()

    fun openGallery()

    fun openCamera()

    fun uploadImageCar()

    fun uploadImageCarSecond()

    fun uploadImageCarThird()

    fun uploadImageAvatar()

    fun uploadImagePassport()

    fun uploadImagePassportBack()

    fun uploadImageId()

    fun uploadImageIdBack()

    fun uploadCarAvatar()

    fun showAvatarImage(bitmap: Bitmap)

    fun showPassportImage(bitmap: Bitmap)

    fun showPassportImageBack(bitmap: Bitmap)

    fun showIdentityImage(bitmap: Bitmap)

    fun showIdentityImageBack(bitmap: Bitmap)

    fun showCarImage(bitmap: Bitmap)

    fun showCarImageSecond(bitmap: Bitmap)

    fun showCarImageThird(bitmap: Bitmap)

    fun showCarAvatar(bitmap: Bitmap)

    fun openListDialogFragment(isMultiple: Boolean, dataList: ArrayList<ListItem>)

    fun showSelectedCarType(title: String)

    fun showSelectedComfort(title: String)

    fun openDriverMainFragment()

    fun showBackBtn(show: Boolean)

}