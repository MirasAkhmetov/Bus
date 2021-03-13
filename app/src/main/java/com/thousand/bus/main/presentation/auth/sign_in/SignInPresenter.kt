package com.thousand.bus.main.presentation.auth.sign_in

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import timber.log.Timber

@InjectViewState
class SignInPresenter(
    private val authInteractor: AuthInteractor
): BasePresenter<SignInView>(){

    private var deviceToken: String = ""

    fun onFirstInit(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                deviceToken = it.result ?: ""
            }
        }
    }

    fun signIn(
        phone: String,
        password: String
    ){
        viewState?.showProgressBar(true)
        authInteractor.signIn(
            phone = phone,
            password = password,
            deviceToken = deviceToken
        ).subscribe(
            {
                it.token?.let { it1 -> LocalStorage.setAccessToken(it1) }
                it.user?.let { it1 -> LocalStorage.setUser(it1) }
                if (it.user?.name == null){
                    if (it.user?.role == AppConstants.ROLE_DRIVER){
                        viewState?.openDriverConfirmFragment()
                    }else{
                        viewState?.openCustomerConfirmFragment()
                    }
                }else{
                    viewState?.openMainCustomerFragment()
                }
                viewState?.showProgressBar(false)
            },
            {
                viewState?.showMessage(it.getErrorMessage())
                viewState?.showProgressBar(false)
            }
        ).connect()
    }
}