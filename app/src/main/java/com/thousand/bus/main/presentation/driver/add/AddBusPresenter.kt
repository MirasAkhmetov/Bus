package com.thousand.bus.main.presentation.driver.add


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.thousand.bus.R
import com.thousand.bus.entity.Car
import com.thousand.bus.entity.CarType
import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.User
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.data.interactor.DriverInteractor
import com.thousand.bus.main.data.interactor.ListInteractor
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.net.URI

@InjectViewState
class AddBusPresenter(
    private val context: Context,
    private val resourceManager: ResourceManager,
    private val driverInteractor: DriverInteractor,
    private val listInteractor: ListInteractor
) : BasePresenter<AddBusView>() {


    private val RC_PASSPORT = 1

    private val RC_CAR = 2

    private val RC_PASSPORT_BACK = 3

    private val RC_CAR_SECOND= 4

    private val RC_CAR_THIRD= 5

    private var currentImageRequestCode = RC_PASSPORT

    private var carTypeList: List<CarType> = listOf()
    private val user = User()


    fun onFirstInit() {
        getCarTypes()
    }

    private fun getCarTypes() {
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


    fun onPassportBtnClicked() {
        currentImageRequestCode = RC_PASSPORT
        viewState?.openCamera()

    }
    fun onPassportBtnClickedGallery() {
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

    fun onCarBtnClicked() {
        currentImageRequestCode = RC_CAR
        viewState?.openCamera()
    }

    fun onCarBtnClickedGallery() {
        currentImageRequestCode = RC_CAR
        viewState?.openGallery()
    }

    fun onCarSecondBtnClicked() {
        currentImageRequestCode = RC_CAR_SECOND
        viewState?.openCamera()
    }

    fun onCarSecondBtnClickedGallery() {
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

    fun onSetImagePath(path: String) {
        getBitmapFromUri(path)
    }

    fun onSetImage(uri: Uri) {
        getBitmapFromUriPhoto(uri)
    }

    private fun getBitmapFromUriPhoto(uri: Uri) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
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
                    if (resource != null) {
                        when (currentImageRequestCode) {

                            RC_PASSPORT -> {
                                user.passportBitmap = resource
                                viewState?.showPassportImage(resource)
                            }
                            RC_PASSPORT_BACK-> {
                                user.passportBackBitmap = resource
                                viewState?.showPassportImageBack(resource)
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


    private fun getBitmapFromUri(path: String) {
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
                    if (resource != null) {
                        when (currentImageRequestCode) {

                            RC_PASSPORT -> {
                                user.passportBitmap = resource
                                viewState?.showPassportImage(resource)
                            }
                            RC_PASSPORT_BACK-> {
                                user.passportBackBitmap = resource
                                viewState?.showPassportImageBack(resource)
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

    fun onCarTypeBtnClicked() {
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

    fun onComfortBtnClicked() {
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

    fun onListItemSelected(listItem: ListItem) {
        when (listItem.requestCode) {
            AppConstants.RC_CAR_TYPE -> {
                if (user.car == null) user.car = Car()
                user.car?.carTypeId = listItem.id
                viewState?.showSelectedCarType(listItem.title ?: "")
            }
        }
    }

    fun onListItemsSelected(listItems: List<ListItem>) {
        if (listItems.isNotEmpty()) {
            when (listItems[0].requestCode) {
                AppConstants.RC_COMFORT -> {
                    if (user.car == null) user.car = Car()
                    user.car?.tv = 0
                    user.car?.baggage = 0
                    user.car?.conditioner = 0
                    var comforts = ""
                    listItems.forEach {
                        when (it.id) {
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
        stateNumber: String
    ) {

        viewState?.showProgressBar(true)

        val params: MutableMap<String, RequestBody> = mutableMapOf()

        params["state_number"] =
            RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), stateNumber)
        params["tv"] = RequestBody.create(
            MediaType.parse(AppConstants.MIME_TYPE_TEXT),
            (user.car?.tv ?: 0).toString()
        )
        params["conditioner"] = RequestBody.create(
            MediaType.parse(AppConstants.MIME_TYPE_TEXT),
            (user.car?.conditioner ?: 0).toString()
        )
        params["baggage"] = RequestBody.create(
            MediaType.parse(AppConstants.MIME_TYPE_TEXT),
            (user.car?.baggage ?: 0).toString()
        )

        user.car?.carTypeId?.let {
            params["car_type_id"] =
                RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_TEXT), it.toString())
        }
        driverInteractor.addBus(
            params = params,
            passportBitmap = user.passportBitmap,
            passportBackBitmap = user.passportBackBitmap,
            carImageBitmap = user.carBitmap,
            carSecondBitmap = user.carSecondBitmap,
            carThirdBitmap = user.carThirdBitmap
        ).subscribe(
            {
                viewState?.openCarListDriverFragment()
                viewState?.showProgressBar(false)
            },
            {
                it.printStackTrace()
                viewState?.showMessage(it.getErrorMessage())
                viewState?.showProgressBar(false)
            }
        ).connect()


    }

}