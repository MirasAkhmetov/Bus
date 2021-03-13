package com.thousand.bus.main.presentation.customer.edit

import android.content.Context
import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import com.thousand.bus.main.data.interactor.DriverInteractor
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

@InjectViewState
class EditProfileCustomerPresenter(
    private val context: Context,
    private val authInteractor: AuthInteractor
): BasePresenter<EditProfileCustomerView>(){

    private var avatarBitmap : Bitmap? = null

    fun onFirstInit(){
        auth(false)
    }

    fun auth(closeThisPage : Boolean){
        viewState?.showProgressBar(true)
        authInteractor.auth()
            .subscribe(
                {
                    viewState?.showProgressBar(false)
                    LocalStorage.setUser(it)
                    if (closeThisPage){
                        viewState?.closeThisFragment()
                    }else {
                        viewState?.showUserInfo(it)
                    }
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onImagePathSet(path: String){
        getBitmapFromUri(path)
    }

    private fun getBitmapFromUri(path: String){
        Glide.with(context)
            .asBitmap()
            .load(File(path))
            .addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource != null){
                        avatarBitmap = resource
                        viewState?.showAvatarImage(resource)
                    }
                    return true
                }
            })
            .submit(250, 250)
    }

    fun onSaveBtnClicked(
        name: String,
        surname: String
    ){
        viewState?.showProgressBar(true)
        val params : MutableMap<String, RequestBody> = mutableMapOf()
        if (name.isNotEmpty()) params["name"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), name)
        if (name.isNotEmpty()) params["surname"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), surname)

        authInteractor.editProfile(
            params = params,
            avatarBitmap = avatarBitmap
        ).subscribe(
            {
                auth(true)
            },
            {
                it.printStackTrace()
                viewState?.showProgressBar(false)
            }
        ).connect()

    }


}
