package com.thousand.bus.main.presentation.common.dialog


import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.CustomerInteractor

@InjectViewState
class FeedbackDialogPresenter(
    private val customerInteractor: CustomerInteractor
) : BasePresenter<FeedbackDialogView>() {

    var carId: Int ? = null


    fun setCarID(carId: Int) {

        if (carId != 0) {
            this.carId = carId
        }
    }


    fun onSubmitBtnClicked(
        text: String?,
        cleanliness: Float,
        behaviour: Float,
        convenience: Float
    ) {
        if (false) {
            viewState?.showLongMessage("Выберите все рэйтинги")
            return
        }

        viewState?.showProgressBar(true)
        customerInteractor.sendFeedback(
            text = text,
            cleanliness = cleanliness,
            behaviour = behaviour,
            convenience = convenience,
            carId = carId
        ).subscribe(
            {
                viewState?.closeDialog()
                viewState?.showLongMessage("Отзыв оставлен")
                viewState?.showProgressBar(false)
            },
            {
                viewState?.showMessage(it.getErrorMessage())
                viewState?.showProgressBar(false)
            }).connect()

    }


}