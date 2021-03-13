package com.thousand.bus.main.presentation.auth.restore.phone

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.restore.password.PasswordRestoreFragment
import kotlinx.android.synthetic.main.fragment_restore_phone.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PhoneRestoreFragment : BaseFragment(), PhoneRestoreView {

    companion object{

        val TAG = "PhoneRestoreFragment"

        fun newInstance(): PhoneRestoreFragment =
            PhoneRestoreFragment()
    }

    @InjectPresenter
    lateinit var presenter: PhoneRestorePresenter

    @ProvidePresenter
    fun providePresenter(): PhoneRestorePresenter {
        getKoin().getScopeOrNull(MainScope.PHONE_RESTORE_SCOPE)?.close()
        return getKoin().createScope(MainScope.PHONE_RESTORE_SCOPE, named(MainScope.PHONE_RESTORE_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_restore_phone

    override fun setUp(savedInstanceState: Bundle?) {
        btnNextRP?.setOnClickListener { presenter.sendBtnClicked(edtPhoneRP.unMasked) }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.PHONE_RESTORE_SCOPE)?.close()
    }

    override fun openRestorePasswordFragment(phone: String) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            PasswordRestoreFragment.newInstance(phone),
            PasswordRestoreFragment.TAG
        )
    }
}