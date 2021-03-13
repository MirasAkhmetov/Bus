package com.thousand.bus.main.presentation.driver.add

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.PhotoBaseFragment
import com.thousand.bus.global.base.pickPhotoFromCameraWithPermissionCheck
import com.thousand.bus.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.list.ListDialogFragment
import com.thousand.bus.main.presentation.driver.carlist.CarListDriverFragment
import kotlinx.android.synthetic.main.fragment_driver_addbus.*
import kotlinx.android.synthetic.main.fragment_order_details.view.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class AddBusFragment : PhotoBaseFragment(), AddBusView {



    companion object {
        val TAG = "AddBusFragment"

        fun newInstance(): AddBusFragment =
            AddBusFragment()
    }

    @InjectPresenter
    lateinit var presenter: AddBusPresenter

    @ProvidePresenter
    fun providePresenter(): AddBusPresenter {
        getKoin().getScopeOrNull(MainScope.ADD_BUS_DRIVER_SCOPE)?.close()
        return getKoin().createScope(
            MainScope.ADD_BUS_DRIVER_SCOPE,
            named(MainScope.ADD_BUS_DRIVER_SCOPE)
        ).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_addbus

    override fun setUp(savedInstanceState: Bundle?) {
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener { }
        }
        txtTitleToolbar?.text = getString(R.string.menu_add_bus)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        imgTechPassDriverAddBus?.setOnClickListener { uploadImagePassport() }
        imgTechPassBackDriverAddBus?.setOnClickListener { uploadImagePassportBack() }
        imgPhotoTransDriverAddBus?.setOnClickListener { uploadImageCar() }
        imgPhotoTransSecondDriverAddBus?.setOnClickListener { uploadImageCarSecond() }
        imgPhotoTransThirdDriverAddBus?.setOnClickListener { uploadImageCarThird() }
        txtCarTypeDriverAddBus?.setOnClickListener { presenter.onCarTypeBtnClicked() }
        txtComfortDriverAddBus?.setOnClickListener { presenter.onComfortBtnClicked() }
        btnConfirmDriverAddBus?.setOnClickListener {
            presenter.onConfirmBtnClicked(
                stateNumber = edtStateNumberDriverAddBus.text.toString()
            )
        }
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.DRIVER_CONFIRM_SCOPE)?.close()
    }

    override fun setImage(uri: Uri) {
        presenter.onSetImage(uri)
    }

    override fun setImagePath(path: String) {
        presenter.onSetImagePath(path)
    }

    override fun setVideo(uri: Uri) {

    }

    override fun setVideoPath(path: String) {

    }


    override fun openGallery() {
        pickPhotoFromGalleryWithPermissionCheck()
    }

    override fun openCamera() {
        pickPhotoFromCameraWithPermissionCheck()
    }


    override fun showPassportImage(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgTechPassDriverAddBus?.setImageBitmap(bitmap)
            imgAddPassport.visible(false)
            imgAddPassport2.visible(true)
        }
    }

    override fun showPassportImageBack(bitmap: Bitmap) {
        activity?.runOnUiThread { imgTechPassBackDriverAddBus?.setImageBitmap(bitmap)
            imgAddPassport2.visible(false)
            imgAddPassport.visible(true)
        }
    }


    override fun showCarImage(bitmap: Bitmap) {
        activity?.runOnUiThread { imgPhotoTransDriverAddBus?.setImageBitmap(bitmap) }
    }

    override fun showCarImageSecond(bitmap: Bitmap) {
        activity?.runOnUiThread { imgPhotoTransSecondDriverAddBus?.setImageBitmap(bitmap) }
    }

    override fun showCarImageThird(bitmap: Bitmap) {
        activity?.runOnUiThread { imgPhotoTransThirdDriverAddBus?.setImageBitmap(bitmap) }
    }


    override fun openListDialogFragment(isMultiple: Boolean, dataList: ArrayList<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(
                isMultiple = isMultiple,
                dataList = dataList,
                onItemSelectedListener = { presenter.onListItemSelected(it) },
                onItemsSelectedListener = { presenter.onListItemsSelected(it) }
            )
                .show(
                    it,
                    ListDialogFragment.TAG
                )
        }
    }

    override fun showSelectedCarType(title: String) {
        txtCarTypeDriverAddBus?.text = title
    }

    override fun showSelectedComfort(title: String) {
        txtComfortDriverAddBus?.text = title
    }

    override fun openCarListDriverFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            CarListDriverFragment.newInstance(cardType = 1),
            CarListDriverFragment.TAG
        )
    }

    override fun uploadImageCar() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onCarBtnClicked()
                    1 -> presenter.onCarBtnClickedGallery()
                }
            }.show()
    }

    override fun uploadImageCarSecond() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onCarSecondBtnClicked()
                    1 -> presenter.onCarSecondBtnClickedGallery()
                }
            }.show()
    }

    override fun uploadImageCarThird() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onCarThirdBtnClicked()
                    1 -> presenter.onCarThirdBtnClickedGallery()
                }
            }.show()
    }



    override fun uploadImagePassport() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onPassportBtnClicked()
                    1 -> presenter.onPassportBtnClickedGallery()
                }
            }.show()
    }

    override fun uploadImagePassportBack() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onPassportBackBtnClicked()
                    1 -> presenter.onPassportBackBtnClickedGallery()
                }
            }.show()
    }


}
