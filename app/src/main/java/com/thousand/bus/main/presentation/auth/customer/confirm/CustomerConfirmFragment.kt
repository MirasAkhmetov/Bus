package com.thousand.bus.main.presentation.auth.customer.confirm

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.PhotoBaseFragment
import com.thousand.bus.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.main.presentation.common.list.ListDialogFragment
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.customer.main.MainCustomerFragment
import kotlinx.android.synthetic.main.fragment_customer_confirm.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class CustomerConfirmFragment : PhotoBaseFragment(), CustomerConfirmView {

    companion object{

        val TAG = "CustomerConfirmFragment"

        fun newInstance(): CustomerConfirmFragment =
            CustomerConfirmFragment()
    }

    @InjectPresenter
    lateinit var presenter: CustomerConfirmPresenter

    @ProvidePresenter
    fun providePresenter(): CustomerConfirmPresenter {
        getKoin().getScopeOrNull(MainScope.CUSTOMER_CONFIRM_SCOPE)?.close()
        return getKoin().createScope(MainScope.CUSTOMER_CONFIRM_SCOPE, named(MainScope.CUSTOMER_CONFIRM_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_confirm

    override fun setUp(savedInstanceState: Bundle?) {
        imgAvatarCustomerConfirm?.setOnClickListener { pickPhotoFromGalleryWithPermissionCheck() }
        btnConfirmCustomerConfirm?.setOnClickListener {
            presenter.onConfirmBtnClicked(
                name = edtNameCustomerConfirm.text.toString(),
                lastName = edtLastNameCustomerConfirm.text.toString()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.CUSTOMER_CONFIRM_SCOPE)?.close()
    }

    override fun setImage(uri: Uri) {

    }

    override fun setImagePath(path: String) {
        presenter.onImagePathSet(path)
    }

    override fun setVideo(uri: Uri) {

    }

    override fun setVideoPath(path: String) {

    }

    override fun openListDialogFragment(dataList: ArrayList<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(
                dataList = dataList,
                onItemSelectedListener = {},
                onItemsSelectedListener = {}
            )
                .show(
                    it,
                    ListDialogFragment.TAG
                )
        }
    }

    override fun showAvatarImage(bitmap: Bitmap) {
        activity?.runOnUiThread { imgAvatarCustomerConfirm?.setImageBitmap(bitmap) }
    }

    override fun showSelectedCity(title: String) {

    }

    override fun openCustomerMainFragment() {
        activity?.replaceFragment(
            R.id.container,
            MainCustomerFragment.newInstance(),
            MainCustomerFragment.TAG
        )
    }
}