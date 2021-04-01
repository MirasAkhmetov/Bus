package com.thousand.bus.main.presentation.common.dialog

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.DriverInteractor

@InjectViewState
class ConfirmPlaceDialogPresenter(
    private val driverInteractor: DriverInteractor
): BasePresenter<ConfirmPlaceDialogView>(){
}