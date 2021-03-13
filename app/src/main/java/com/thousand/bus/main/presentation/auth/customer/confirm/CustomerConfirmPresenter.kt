package com.thousand.bus.main.presentation.auth.customer.confirm

import android.content.Context
import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.messaging.FirebaseMessaging
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

@InjectViewState
class CustomerConfirmPresenter(
    private val context: Context,
    private val authInteractor: AuthInteractor
): BasePresenter<CustomerConfirmView>(){

    private var avatarBitmap : Bitmap? = null
    private var deviceToken: String? = null

    fun onFirstInit(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) deviceToken = it.result
        }
    }

    fun onConfirmBtnClicked(
        name: String,
        lastName: String
    ){

        viewState?.showProgressBar(true)

        val params: MutableMap<String, RequestBody> = mutableMapOf()
        params["name"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), name)
        params["surname"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), lastName)
        params["device_token"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), deviceToken ?: "")

        authInteractor.confirmation(
            params = params,
            avatarBitmap = avatarBitmap
        )
            .subscribe(
                {
                    LocalStorage.setUser(it)
                    viewState?.openCustomerMainFragment()
                    viewState?.showProgressBar(false)
                },
                {
                    viewState?.showMessage(it.getErrorMessage())
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

}