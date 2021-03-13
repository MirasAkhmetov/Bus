package com.thousand.bus.main.presentation.activity

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor

@InjectViewState
class MainActivityPresenter(
    private val authInteractor: AuthInteractor
): BasePresenter<MainActivityView>(){

    fun onFirstInit(){
        viewState?.openWelcomeFragment()
        Handler().postDelayed(
            {
                if (LocalStorage.getAccessToken() == LocalStorage.PREF_NO_VAL){
                    viewState?.openSignInFragment()
                }else{
                    auth()
                }
            },
            3000
        )
    }

    private fun auth(){
        authInteractor.auth()
            .subscribe(
                {
                    LocalStorage.setUser(it)
                    if (it.name == null){
                        if (it.role == AppConstants.ROLE_DRIVER){
                            viewState?.openDriverConfirmFragment()
                        }else{
                            viewState?.openCustomerConfirmFragment()
                        }
                    }else{
                        if (it.role == AppConstants.ROLE_DRIVER) {
                            viewState?.openMainDriverFragment()
                        }else{
                            viewState?.openMainCustomerFragment()
                        }
                    }
                },
                {
                    it.printStackTrace()
                    viewState?.openSignInFragment()
                }
            ).connect()
    }

}