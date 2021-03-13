package com.thousand.bus.main.presentation.driver.add

import android.graphics.Bitmap
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.BaseMvpView

interface AddBusView: BaseMvpView {
    fun openGallery()

    fun openCamera()

    fun uploadImageCar()

    fun uploadImageCarSecond()

    fun uploadImageCarThird()

    fun uploadImagePassport()

    fun uploadImagePassportBack()

    fun showPassportImage(bitmap: Bitmap)

    fun showPassportImageBack(bitmap: Bitmap)

    fun showCarImage(bitmap: Bitmap)

    fun showCarImageSecond(bitmap: Bitmap)

    fun showCarImageThird(bitmap: Bitmap)

    fun openListDialogFragment(isMultiple: Boolean, dataList: ArrayList<ListItem>)

    fun showSelectedCarType(title: String)

    fun showSelectedComfort(title: String)

    fun openCarListDriverFragment()

}