package com.thousand.bus.main.presentation.common.dialog

import android.os.Bundle
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseDialogFragment
import com.thousand.bus.main.di.MainScope
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ConfirmPlaceDialogFragment : BaseDialogFragment(), ConfirmPlaceDialogView {

    companion object{
        val TAG = "ConfirmPlaceDialogFragment"
    }

    @InjectPresenter
    lateinit var presenter: ConfirmPlaceDialogPresenter

    @ProvidePresenter
    fun providePresenter(): ConfirmPlaceDialogPresenter {
        getKoin().getScopeOrNull(MainScope.CONFIRM_PLACE_DIALOG_SCOPE)?.close()
        return getKoin().createScope(
            MainScope.CONFIRM_PLACE_DIALOG_SCOPE,
            named(MainScope.CONFIRM_PLACE_DIALOG_SCOPE)
        ).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_dialog_confirm_place

    override fun setUp(savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun closeDialog() {
        dismiss()
    }
}