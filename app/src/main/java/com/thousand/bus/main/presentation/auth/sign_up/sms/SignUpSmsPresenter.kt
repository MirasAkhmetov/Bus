package com.thousand.bus.main.presentation.auth.sign_up.sms

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.R
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor

@InjectViewState
class SignUpSmsPresenter(
    private val phone: String,
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor
): BasePresenter<SignUpSmsView>(){

    fun onConfirmBtnClicked(code: String){

        authInteractor.smsConfirmation(phone, code)
            .subscribe(
                {
                    it.token?.let { it1 -> LocalStorage.setAccessToken(it1) }
                    it.user?.let { it1 -> LocalStorage.setUser(it1) }
                    if (LocalStorage.getUser()?.role == AppConstants.ROLE_DRIVER){
                        viewState?.openDriverConfirmFragment()
                    }else{
                        viewState?.openCustomerConfirmFragment()
                    }
                },
                {
                    it.printStackTrace()
                    viewState?.showMessage(resourceManager.getString(R.string.auth_wrong_sms_code))
                }
            ).connect()

    }

}