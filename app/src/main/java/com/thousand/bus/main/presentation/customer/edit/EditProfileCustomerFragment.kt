package com.thousand.bus.main.presentation.customer.edit

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.thousand.bus.R
import com.thousand.bus.entity.User
import com.thousand.bus.global.base.PhotoBaseFragment
import com.thousand.bus.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.thousand.bus.global.di.ServiceProperties
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_custome_edit_profile.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class EditProfileCustomerFragment : PhotoBaseFragment(), EditProfileCustomerView {

    companion object{

        val TAG = "EditProfileCustomerFragment"

        fun newInstance(): EditProfileCustomerFragment =
            EditProfileCustomerFragment()
    }

    @InjectPresenter
    lateinit var presenter: EditProfileCustomerPresenter


    @ProvidePresenter
    fun providePresenter(): EditProfileCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.EDIT_PROFILE_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(MainScope.EDIT_PROFILE_CUSTOMER_SCOPE, named(MainScope.EDIT_PROFILE_CUSTOMER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_custome_edit_profile

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.edit)
        presenter.onFirstInit()
        btnSaveCEP?.setOnClickListener {
            presenter.onSaveBtnClicked(
                name = edtNameCEP.text.toString(),
                surname = edtLastNameCEP.text.toString()
            )
        }
        imgAvatarCEP?.setOnClickListener { pickPhotoFromGalleryWithPermissionCheck() }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.EDIT_PROFILE_CUSTOMER_SCOPE)?.close()
    }

    override fun showUserInfo(user: User) {
        context?.let {
            Glide.with(it)
                .asBitmap()
                .load("${ServiceProperties.SERVER_URL}/${user.avatar}")
                .addListener(object: RequestListener<Bitmap>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imgAvatarCEP.setImageDrawable(ContextCompat.getDrawable(it, R.drawable.ic_4))
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        activity?.runOnUiThread { imgAvatarCEP.setImageBitmap(resource) }
                        return true
                    }
                }).submit()
        }
        edtNameCEP?.setText(user.name)
        edtLastNameCEP?.setText(user.surname)
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

    override fun showAvatarImage(bitmap: Bitmap) {
        activity?.runOnUiThread { imgAvatarCEP?.setImageBitmap(bitmap) }
    }

    override fun closeThisFragment() {
        activity?.onBackPressed()
    }
}