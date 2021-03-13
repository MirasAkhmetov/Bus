package com.thousand.bus.main.presentation.common.profile

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.TermAndPolicy
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import com.thousand.bus.main.data.interactor.ListInteractor
import okhttp3.MediaType
import okhttp3.RequestBody

@InjectViewState
class ProfilePresenter(
    private val role: String,
    private val authInteractor: AuthInteractor,
    private val listInteractor: ListInteractor
): BasePresenter<ProfileView>(){

    private var termAndPolicy: TermAndPolicy? = null

    fun onFirstInit(){
        getTermsAndPolicy()
    }

    private fun getTermsAndPolicy(){
        viewState?.showProgressBar(true)
        listInteractor.getTermsAndPolicy()
            .subscribe(
                {
                    termAndPolicy = it
                    auth()
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun auth(){
        authInteractor.auth()
            .subscribe(
                {
                    viewState?.showUserInfo(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onNotificationChecked(isChecked: Boolean){
        val params: MutableMap<String, RequestBody> = mutableMapOf()
        if (isChecked)
            params["push"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), "1")
        else
            params["push"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), "0")
        editProfile(params)
    }

    fun onSoundChecked(isChecked: Boolean){
        val params: MutableMap<String, RequestBody> = mutableMapOf()
        if (isChecked)
            params["sound"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), "1")
        else
            params["sound"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), "0")
        editProfile(params)
    }

    private fun editProfile(params: Map<String, RequestBody>){
        authInteractor.editProfile(params)
            .subscribe(
                {

                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onEditBtnClicked(){
        viewState?.openEditProfileCustomerFragment()
    }

    fun onAboutBtnClicked(){
        termAndPolicy?.termsOfUse?.let { viewState?.openWebViewFragment(it) }
    }

    fun onSignOutBtnClicked(){
        viewState?.showProgressBar(true)
        authInteractor.logout(LocalStorage.getAccessToken())
            .subscribe(
                {
                    LocalStorage.setUser(null)
                    LocalStorage.setAccessToken(null)
                    viewState?.openSignInFragment()
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    LocalStorage.setUser(null)
                    LocalStorage.setAccessToken(null)
                    viewState?.openSignInFragment()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }
}