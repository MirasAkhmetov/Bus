package com.thousand.bus.main.presentation.auth.restore.password

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.R
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.main.data.interactor.AuthInteractor

@InjectViewState
class PasswordRestorePresenter(
    private val phone: String,
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor
): BasePresenter<PasswordRestoreView>(){


    fun onRestoreBtnClicked(
        code: String,
        password: String,
        rePassword: String
    ){

        if (password != rePassword){
            viewState?.showMessage(resourceManager.getString(R.string.passwords_not_same))
            return
        }

        viewState?.showProgressBar(true)

        authInteractor.restorePassword(
            phone = phone,
            code = code,
            password = password
        ).subscribe(
            {
                viewState?.showMessage(resourceManager.getString(R.string.password_restored))
                viewState?.openSignInFragment()
            },
            {
                viewState?.showMessage(it.getErrorMessage())
                viewState?.showProgressBar(false)
            }
        ).connect()

    }

}