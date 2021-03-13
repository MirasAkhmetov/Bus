package com.thousand.bus.main.presentation.auth.restore.password

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.sign_in.SignInFragment
import kotlinx.android.synthetic.main.fragment_restore_password.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PasswordRestoreFragment : BaseFragment(), PasswordRestoreView {

    companion object{

        val TAG = "PasswordRestoreFragment"

        private val BUNDLE_PHONE = "phone"

        fun newInstance(phone: String): PasswordRestoreFragment =
            PasswordRestoreFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_PHONE, phone)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: PasswordRestorePresenter

    @ProvidePresenter
    fun providePresenter(): PasswordRestorePresenter {
        getKoin().getScopeOrNull(MainScope.PASSWORD_RESTORE_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.PASSWORD_RESTORE_SCOPE, named(MainScope.PASSWORD_RESTORE_SCOPE))
        val phone = arguments?.getString(BUNDLE_PHONE)
        return scope.get { parametersOf(phone) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_restore_password

    override fun setUp(savedInstanceState: Bundle?) {
        btnNextRP?.setOnClickListener {
            presenter.onRestoreBtnClicked(
                code = edtCodeRP.text.toString(),
                password = edtPasswordRP.text.toString(),
                rePassword = edtConfirmPasswordRP.text.toString()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.PASSWORD_RESTORE_SCOPE)?.close()
    }

    override fun openSignInFragment() {
        activity?.supportFragmentManager
            ?.replaceFragment(
                R.id.container,
                SignInFragment.newInstance(),
                SignInFragment.TAG
            )
    }
}