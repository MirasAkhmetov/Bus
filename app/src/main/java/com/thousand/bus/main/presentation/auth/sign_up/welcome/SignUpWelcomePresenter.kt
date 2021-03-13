package com.thousand.bus.main.presentation.auth.sign_up.welcome

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.thousand.bus.R
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.extension.getObjectErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import com.thousand.bus.main.data.interactor.ListInteractor

@InjectViewState
class SignUpWelcomePresenter(
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor,
    private val listInteractor: ListInteractor
): BasePresenter<SignUpWelcomeView>(){

    private var currentRole = AppConstants.ROLE_DRIVER
    private var deviceToken: String = ""

    fun onFirstInit(){
        viewState?.showUserRoleData(
            arrayListOf(
                resourceManager.getString(R.string.passenger),
                resourceManager.getString(R.string.driver)
            )
        )
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful)
                deviceToken = it.result ?: ""
        }
    }

    fun onRoleItemSelected(position: Int){
        when(position){
            0 -> currentRole = AppConstants.ROLE_PASSENGER
            1 -> currentRole = AppConstants.ROLE_DRIVER
        }
    }

    fun onNextBtnClicked(
        phone: String,
        password: String,
        rePassword: String,
        isAgreeWithPolicy: Boolean
    ){
        if (phone.isEmpty() || password.isEmpty() || rePassword.isEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.fill_all_field))
            return
        }

        if (phone.length != 10){
            viewState?.showMessage(resourceManager.getString(R.string.auth_wrong_phone_format))
            return
        }

        if (password != rePassword){
            viewState?.showMessage(resourceManager.getString(R.string.auth_passwords_not_same))
            return
        }

        if (!isAgreeWithPolicy){
            viewState?.showMessage(resourceManager.getString(R.string.auth_agree_with_policy))
            return
        }

        viewState?.showProgressBar(true)
        authInteractor.registration(
            phone = phone,
            password = password,
            role = currentRole,
            deviceToken = deviceToken
        ).subscribe(
            {
                viewState?.openSignUpSmsFragment(phone)
                viewState?.showProgressBar(false)
            },
            {
                viewState?.showMessage(it.getErrorMessage())
                viewState?.showProgressBar(false)
            }
        ).connect()
    }

    fun getSettings(){
        viewState?.showProgressBar(true)
        listInteractor.getTermsAndPolicy()
            .subscribe(
                {
                    it.privacyPolicy?.let { viewState?.openWebViewFragment(it) }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

}