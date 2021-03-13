package com.thousand.bus.main.presentation.auth.driver.confirm

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.messaging.FirebaseMessaging
import com.thousand.bus.R
import com.thousand.bus.entity.Car
import com.thousand.bus.entity.CarType
import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.User
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.AuthInteractor
import com.thousand.bus.main.data.interactor.ListInteractor
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

@InjectViewState
class DriverConfirmPresenter(
    private val isRoleChange : Boolean,
    private val context: Context,
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor,
    private val listInteractor: ListInteractor
): BasePresenter<DriverConfirmView>(){

    private val RC_AVATAR = 1
    private val RC_PASSPORT = 2
    private val RC_IDENTITY = 3
    private val RC_CAR = 4
    private val RC_PASSPORT_BACK = 5
    private val RC_IDENTITY_BACK = 6
    private val RC_CAR_SECOND = 7
    private val RC_CAR_THIRD = 8

    private var currentImageRequestCode = RC_AVATAR

    private var carTypeList: List<CarType> = listOf()
    private val user = User()
    private var deviceToken: String? = null

    fun onFirstInit(){
        viewState?.showBackBtn(isRoleChange)
        getCarTypes()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) deviceToken = it.result
        }
    }

    private fun getCarTypes(){
        listInteractor.getCarTypes()
            .subscribe(
                {
                    carTypeList = it
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onAvatarBtnClicked(){
        currentImageRequestCode = RC_AVATAR
        viewState?.openCamera()
    }

    fun onAvatarBtnClickedGallery(){
        currentImageRequestCode = RC_AVATAR
        viewState?.openGallery()
    }

    fun onPassportBtnClicked(){
        currentImageRequestCode = RC_PASSPORT
        viewState?.openCamera()
    }

    fun onPassportBtnClickedGallery(){
        currentImageRequestCode = RC_PASSPORT
        viewState?.openGallery()
    }

    fun onPassportBackBtnClicked() {
        currentImageRequestCode = RC_PASSPORT_BACK
        viewState?.openCamera()

    }
    fun onPassportBackBtnClickedGallery() {
        currentImageRequestCode = RC_PASSPORT_BACK
        viewState?.openGallery()
    }

    fun onIdentityBtnClicked(){
        currentImageRequestCode = RC_IDENTITY
        viewState?.openCamera()
    }

    fun onIdentityBtnClickedGallery(){
        currentImageRequestCode = RC_IDENTITY
        viewState?.openGallery()
    }

    fun onIdentityBackBtnClicked(){
        currentImageRequestCode = RC_IDENTITY_BACK
        viewState?.openCamera()
    }

    fun onIdentityBackBtnClickedGallery(){
        currentImageRequestCode = RC_IDENTITY_BACK
        viewState?.openGallery()
    }

    fun onCarBtnClicked(){
        currentImageRequestCode = RC_CAR
        viewState?.openCamera()
    }

    fun onCarBtnClickedGallery(){
        currentImageRequestCode = RC_CAR
        viewState?.openGallery()
    }

    fun onCarSecondBtnClicked(){
        currentImageRequestCode = RC_CAR_SECOND
        viewState?.openCamera()
    }

    fun onCarSecondBtnClickedGallery(){
        currentImageRequestCode = RC_CAR_SECOND
        viewState?.openGallery()
    }

    fun onCarThirdBtnClicked() {
        currentImageRequestCode = RC_CAR_THIRD
        viewState?.openCamera()
    }

    fun onCarThirdBtnClickedGallery() {
        currentImageRequestCode = RC_CAR_THIRD
        viewState?.openGallery()
    }
    fun onSetImagePath(path: String){
        getBitmapFromUri(path)
    }


    fun onSetImage(uri: Uri) {
        getBitmapFromUriPhoto(uri)
    }

    private fun getBitmapFromUri(path: String){
        Glide.with(context)
            .asBitmap()
            .load(File(path))
            .addListener(object : RequestListener<Bitmap>{
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
                        when(currentImageRequestCode){
                            RC_AVATAR -> {
                                user.avatarBitmap = resource
                                viewState?.showAvatarImage(resource)
                            }
                            RC_PASSPORT -> {
                                user.passportBitmap = resource
                                viewState?.showPassportImage(resource)
                            }
                            RC_PASSPORT_BACK -> {
                                user.passportBackBitmap = resource
                                viewState?.showPassportImageBack(resource)
                            }
                            RC_IDENTITY -> {
                                user.identityBitmap = resource
                                viewState?.showIdentityImage(resource)
                            }
                            RC_IDENTITY_BACK -> {
                                user.identityBackBitmap = resource
                                viewState?.showIdentityImageBack(resource)
                            }
                            RC_CAR -> {
                                user.carBitmap = resource
                                viewState?.showCarImage(resource)
                            }
                            RC_CAR_SECOND -> {
                                user.carSecondBitmap = resource
                                viewState?.showCarImageSecond(resource)
                            }
                            RC_CAR_THIRD -> {
                                user.carThirdBitmap = resource
                                viewState?.showCarImageThird(resource)
                            }
                        }
                    }
                    return true
                }
            })
            .submit(250, 250)
    }

    private fun getBitmapFromUriPhoto(uri: Uri){
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .addListener(object : RequestListener<Bitmap>{
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
                        when(currentImageRequestCode){
                            RC_AVATAR -> {
                                user.avatarBitmap = resource
                                viewState?.showAvatarImage(resource)
                            }
                            RC_PASSPORT -> {
                                user.passportBitmap = resource
                                viewState?.showPassportImage(resource)
                            }
                            RC_PASSPORT_BACK -> {
                                user.passportBackBitmap = resource
                                viewState?.showPassportImageBack(resource)
                            }
                            RC_IDENTITY -> {
                                user.identityBitmap = resource
                                viewState?.showIdentityImage(resource)
                            }
                            RC_IDENTITY_BACK -> {
                                user.identityBackBitmap = resource
                                viewState?.showIdentityImageBack(resource)
                            }
                            RC_CAR -> {
                                user.carBitmap = resource
                                viewState?.showCarImage(resource)
                            }
                            RC_CAR_SECOND -> {
                                user.carSecondBitmap = resource
                                viewState?.showCarImageSecond(resource)
                            }
                            RC_CAR_THIRD -> {
                                user.carThirdBitmap = resource
                                viewState?.showCarImageThird(resource)
                            }
                        }
                    }
                    return true
                }
            })
            .submit(250, 250)
    }

    fun onCarTypeBtnClicked(){
        val tempList: MutableList<ListItem> = mutableListOf()
        carTypeList.forEach {
            tempList.add(
                ListItem(
                    id = it.id,
                    title = "${it.name} - ${it.countPlaces} ${resourceManager.getString(R.string.places)}",
                    requestCode = AppConstants.RC_CAR_TYPE
                )
            )
        }
        viewState?.openListDialogFragment(false, ArrayList(tempList))
    }

    fun onComfortBtnClicked(){
        val tempList: MutableList<ListItem> = mutableListOf()
        tempList.add(
            ListItem(
                id = 1,
                title = resourceManager.getString(R.string.order_television),
                requestCode = AppConstants.RC_COMFORT
            )
        )
        tempList.add(
            ListItem(
                id = 2,
                title = resourceManager.getString(R.string.order_baggage),
                requestCode = AppConstants.RC_COMFORT
            )
        )
        tempList.add(
            ListItem(
                id = 3,
                title = resourceManager.getString(R.string.order_air_condition),
                requestCode = AppConstants.RC_COMFORT
            )
        )
        viewState?.openListDialogFragment(true, ArrayList(tempList))
    }

    fun onListItemSelected(listItem: ListItem){
        when(listItem.requestCode){
            AppConstants.RC_CAR_TYPE -> {
                if (user.car == null) user.car = Car()
                user.car?.carTypeId = listItem.id
                viewState?.showSelectedCarType(listItem.title ?: "")
            }
        }
    }

    fun onListItemsSelected(listItems: List<ListItem>){
        if (listItems.isNotEmpty()){
            when(listItems[0].requestCode){
                AppConstants.RC_COMFORT -> {
                    if (user.car == null) user.car = Car()
                    user.car?.tv = 0
                    user.car?.baggage = 0
                    user.car?.conditioner = 0
                    var comforts = ""
                    listItems.forEach {
                        when(it.id){
                            1 -> {
                                user.car?.tv = 1
                                comforts += "${resourceManager.getString(R.string.order_television)} , "
                            }
                            2 -> {
                                user.car?.baggage = 1
                                comforts += "${resourceManager.getString(R.string.order_baggage)} , "
                            }
                            3 -> {
                                user.car?.conditioner = 1
                                comforts += "${resourceManager.getString(R.string.order_air_condition)} , "
                            }
                        }
                    }
                    if (comforts.isNotEmpty()) comforts = comforts.substring(0, comforts.length - 2)
                    viewState?.showSelectedComfort(comforts)
                }
            }
        }
    }

    fun onConfirmBtnClicked(
        name: String,
        lastName: String,
        stateNumber: String,
        kaspiNumber: String,
        nameBank: String

    ){

        viewState?.showProgressBar(true)

        val params: MutableMap<String, RequestBody> = mutableMapOf()
        params["name"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), name)
        params["surname"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), lastName)
        params["state_number"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), stateNumber)
        params["bank_card"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), kaspiNumber)
        params["card_fullname"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), nameBank)
        params["tv"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), (user.car?.tv ?: 0).toString())
        params["conditioner"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), (user.car?.conditioner ?: 0).toString())
        params["baggage"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), (user.car?.baggage ?: 0).toString())
        params["device_token"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), deviceToken ?: "")
        user.car?.carTypeId?.let { params["car_type_id"] = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), it.toString()) }

        if (!isRoleChange){
            authInteractor.confirmation(
                params = params,
                avatarBitmap = user.avatarBitmap,
                passportBitmap = user.passportBitmap,
                passportBackBitmap = user.passportBackBitmap,
                identityBitmap = user.identityBitmap,
                identityBackBitmap = user.identityBackBitmap,
                carImageBitmap = user.carBitmap,
                carSecondBitmap = user.carSecondBitmap,
                carThirdBitmap = user.carThirdBitmap
            ).subscribe(
                {
                    LocalStorage.setUser(it)
                    viewState?.openDriverMainFragment()
                    viewState?.showProgressBar(false)
                },
                {
                    viewState?.showMessage(it.getErrorMessage())
                    viewState?.showProgressBar(false)
                }
            ).connect()
        }else{
            authInteractor.roleDriver(
                params = params,
                avatarBitmap = user.avatarBitmap,
                passportBitmap = user.passportBitmap,
                passportBackBitmap = user.passportBackBitmap,
                identityBitmap = user.identityBitmap,
                identityBackBitmap = user.identityBackBitmap,
                carImageBitmap = user.carBitmap,
                carSecondBitmap = user.carSecondBitmap,
                carThirdBitmap = user.carThirdBitmap
            ).subscribe(
                {
                    authInteractor.auth()
                        .subscribe(
                            {
                                LocalStorage.setUser(it)
                                viewState?.openDriverMainFragment()
                                viewState?.showProgressBar(false)
                            },
                            {
                                viewState?.openDriverMainFragment()
                                viewState?.showProgressBar(false)
                            }
                        ).connect()
                },
                {
                    viewState?.showMessage(it.getErrorMessage())
                    viewState?.showProgressBar(false)
                }
            ).connect()
        }

    }

}