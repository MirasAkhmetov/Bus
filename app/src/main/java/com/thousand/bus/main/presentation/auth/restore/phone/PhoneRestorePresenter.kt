package com.thousand.bus.main.presentation.auth.restore.phone

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.main.data.interactor.AuthInteractor

@InjectViewState
class PhoneRestorePresenter(
    private val authInteractor: AuthInteractor
): BasePresenter<PhoneRestoreView>(){

    fun sendBtnClicked(phone: String){
        if (phone.isEmpty())
            return

        viewState?.showProgressBar(true)

        authInteractor.sendSmsForPasswordRestore(phone)
            .subscribe(
                {
                    viewState?.showMessage(it)
                    viewState?.openRestorePasswordFragment(phone)
                    viewState?.showProgressBar(false)
                },
                {
                    viewState?.showMessage(it.getErrorMessage())
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

}