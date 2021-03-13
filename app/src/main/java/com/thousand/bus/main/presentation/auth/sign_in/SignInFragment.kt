package com.thousand.bus.main.presentation.auth.sign_in

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.customer.confirm.CustomerConfirmFragment
import com.thousand.bus.main.presentation.auth.driver.confirm.DriverConfirmFragment
import com.thousand.bus.main.presentation.auth.restore.phone.PhoneRestoreFragment
import com.thousand.bus.main.presentation.auth.sign_up.welcome.SignUpWelcomeFragment
import com.thousand.bus.main.presentation.customer.main.MainCustomerFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber

class SignInFragment : BaseFragment(), SignInView {

    companion object{

        val TAG = "SignInFragment"

        fun newInstance(): SignInFragment =
            SignInFragment()
    }

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter(): SignInPresenter {
        getKoin().getScopeOrNull(MainScope.SIGN_IN_SCOPE)?.close()
        return getKoin().createScope(MainScope.SIGN_IN_SCOPE, named(MainScope.SIGN_IN_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_sign_in

    override fun setUp(savedInstanceState: Bundle?) {
        btnSignInSignIn?.setOnClickListener { presenter.signIn(edtPhoneSignIn.unMasked, edtPasswordSignIn.text.toString()) }
        btnCreateAccountSignIn?.setOnClickListener { openSignUpWelcomeFragment() }
        txtRestoreSignIn?.setOnClickListener { openRestorePhoneFragment() }
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.SIGN_IN_SCOPE)?.close()
    }

    override fun openSignUpWelcomeFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            SignUpWelcomeFragment.newInstance(),
            SignUpWelcomeFragment.TAG
        )
    }

    override fun openMainCustomerFragment() {
        activity?.replaceFragment(
            R.id.container,
            MainCustomerFragment.newInstance(),
            MainCustomerFragment.TAG
        )
    }

    override fun openCustomerConfirmFragment() {
        activity?.replaceFragment(
            R.id.container,
            CustomerConfirmFragment.newInstance(),
            CustomerConfirmFragment.TAG
        )
    }

    override fun openDriverConfirmFragment() {
        activity?.replaceFragment(
            R.id.container,
            DriverConfirmFragment.newInstance(),
            DriverConfirmFragment.TAG
        )
    }

    override fun openRestorePhoneFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            PhoneRestoreFragment.newInstance(),
            PhoneRestoreFragment.TAG
        )
    }

}