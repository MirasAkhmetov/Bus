package com.thousand.bus.main.presentation.auth.driver.confirm

import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.PhotoBaseFragment
import com.thousand.bus.global.base.pickPhotoFromCameraWithPermissionCheck
import com.thousand.bus.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.presentation.common.list.ListDialogFragment
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.driver.wait.DriverConfirmWaitFragment
import com.thousand.bus.main.presentation.driver.main.MainDriverFragment
import kotlinx.android.synthetic.main.dialog_bank_card.view.*
import kotlinx.android.synthetic.main.fragment_driver_addbus.*
import kotlinx.android.synthetic.main.fragment_driver_confirm.*
import kotlinx.android.synthetic.main.fragment_driver_confirm_wait.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class DriverConfirmFragment : PhotoBaseFragment(), DriverConfirmView {

    private var phoneBank : String? = null
    private var nameBank : String? = null

    companion object{

        val TAG = "DriverConfirmFragment"

        private val BUNDLE_IS_ROLE_CHANGE = "is_role_change"

        fun newInstance(isRoleChange: Boolean = false): DriverConfirmFragment =
            DriverConfirmFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(BUNDLE_IS_ROLE_CHANGE, isRoleChange)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: DriverConfirmPresenter

    @ProvidePresenter
    fun providePresenter(): DriverConfirmPresenter {
        getKoin().getScopeOrNull(MainScope.DRIVER_CONFIRM_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.DRIVER_CONFIRM_SCOPE, named(MainScope.DRIVER_CONFIRM_SCOPE))
        val isRoleChange = arguments?.getBoolean(BUNDLE_IS_ROLE_CHANGE)
        return scope.get { parametersOf(isRoleChange) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_confirm

    override fun setUp(savedInstanceState: Bundle?) {
        imgAvatarDriverConfirm?.setOnClickListener { presenter.onAvatarBtnClicked() }
        imgTechPassDriverConfirm?.setOnClickListener { uploadImagePassport() }
        imgTechPassBackDriverConfirm?.setOnClickListener { uploadImagePassportBack() }
        imgUdLichDriverConfirm?.setOnClickListener { uploadImageId() }
        imgUdLichBackDriverConfirm?.setOnClickListener { uploadImageIdBack() }
        imgPhotoTransDriverConfirm?.setOnClickListener { uploadImageCar() }
        imgPhotoTransSecondDriverConfirm?.setOnClickListener { uploadImageCarSecond() }
        imgPhotoTransThirdDriverConfirm?.setOnClickListener { uploadImageCarThird() }
        imgCarAvatarDriverConfirm?.setOnClickListener { uploadCarAvatar() }
        btnConfirmDriverConfirm?.setOnClickListener { openDriverConfirmWaitFragment() }
        txtCarTypeDriverConfirm?.setOnClickListener { presenter.onCarTypeBtnClicked() }
        txtComfortDriverConfirm?.setOnClickListener { presenter.onComfortBtnClicked() }
        txtBankCardDriverConfirm?.setOnClickListener{ onBankCardBtnClicked()}
        btnConfirmDriverConfirm?.setOnClickListener {
            presenter.onConfirmBtnClicked(
                name = edtNameDriverConfirm.text.toString(),
                lastName = edtLastNameDriverConfirm.text.toString(),
                stateNumber = edtStateNumberDriverConfirm.text.toString(),
                kaspiNumber = phoneBank.toString(),
                nameBank = nameBank.toString()
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

    override fun openDriverConfirmWaitFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            DriverConfirmWaitFragment.newInstance(),
            DriverConfirmWaitFragment.TAG
        )
    }

    override fun openGallery() {
        pickPhotoFromGalleryWithPermissionCheck()
    }

    override fun openCamera() {
        pickPhotoFromCameraWithPermissionCheck()
    }


    override fun showAvatarImage(bitmap: Bitmap) {
        activity?.runOnUiThread { imgAvatarDriverConfirm?.setImageBitmap(bitmap) }
    }

    override fun showPassportImage(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgTechPassDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showPassportImageBack(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgTechPassBackDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showIdentityImage(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgUdLichDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showIdentityImageBack(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgUdLichBackDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showCarImage(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgPhotoTransDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showCarImageSecond(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgPhotoTransSecondDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showCarImageThird(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgPhotoTransThirdDriverConfirm?.setImageBitmap(bitmap)
        }
    }

    override fun showCarAvatar(bitmap: Bitmap) {
        activity?.runOnUiThread {
            imgCarAvatarDriverConfirm?.setImageBitmap(bitmap)
        }
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
        txtCarTypeDriverConfirm?.text = title
    }

    override fun openDriverMainFragment() {
        activity?.replaceFragment(
            R.id.container,
            MainDriverFragment.newInstance(),
            MainDriverFragment.TAG
        )
    }

    private fun onBankCardBtnClicked(){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_bank_card, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
        val  mAlertDialog = mBuilder.show()

        dialogView.btnBankOk.setOnClickListener{

             phoneBank = dialogView.edtBankPhone.unMasked
             nameBank = dialogView.edtBankName.text.toString()
            if (phoneBank!!.isEmpty() || nameBank!!.isEmpty())
                mAlertDialog.dismiss()
            else{
                mAlertDialog.dismiss()
                txtBankCardDriverConfirm.setText(phoneBank + "  " + nameBank)
            }

        }
    }


    override fun showSelectedComfort(title: String) {
        txtComfortDriverConfirm?.text = title
    }

    override fun showBackBtn(show: Boolean) {
        imgBackDriverConfirm?.visible(show)
        imgBackDriverConfirm?.setOnClickListener { activity?.onBackPressed() }
    }

    override fun uploadImageAvatar() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onAvatarBtnClicked()
                    1 -> presenter.onAvatarBtnClickedGallery()
                }
            }.show()
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

    override fun uploadCarAvatar() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onCarAvatarClicked()
                    1 -> presenter.onCarAvatarClickedGallery()
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


    override fun uploadImageId() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onIdentityBtnClicked()
                    1 -> presenter.onIdentityBtnClickedGallery()
                }
            }.show()
    }

    override fun uploadImageIdBack() {
        AlertDialog.Builder(context)
            .setItems(arrayOf("Сделать снимок", "Выбрать из галереи")) { _, which ->
                when (which) {
                    0 -> presenter.onIdentityBackBtnClicked()
                    1 -> presenter.onIdentityBackBtnClickedGallery()
                }
            }.show()
    }


}