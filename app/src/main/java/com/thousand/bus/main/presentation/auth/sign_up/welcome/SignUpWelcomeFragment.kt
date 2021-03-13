package com.thousand.bus.main.presentation.auth.sign_up.welcome

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.sign_up.sms.SignUpSmsFragment
import com.thousand.bus.main.presentation.common.web.WebViewFragment
import kotlinx.android.synthetic.main.fragment_sign_up_welcome.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SignUpWelcomeFragment : BaseFragment(), SignUpWelcomeView {

    companion object{

        val TAG = "SignUpWelcomeFragment"

        fun newInstance(): SignUpWelcomeFragment =
            SignUpWelcomeFragment()
    }

    @InjectPresenter
    lateinit var presenter: SignUpWelcomePresenter

    @ProvidePresenter
    fun providePresenter(): SignUpWelcomePresenter {
        getKoin().getScopeOrNull(MainScope.SIGN_UP_WELCOME_SCOPE)?.close()
        return getKoin().createScope(MainScope.SIGN_UP_WELCOME_SCOPE, named(MainScope.SIGN_UP_WELCOME_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_sign_up_welcome

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit()
        spinnerTypeSUW?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.onRoleItemSelected(position)
            }
        }
        btnNextSUW?.setOnClickListener {
            presenter.onNextBtnClicked(
                phone = edtPhoneSUW.unMasked,
                password = edtPasswordSUW.text.toString(),
                rePassword = edtConfirmPasswordSUW.text.toString(),
                isAgreeWithPolicy = checkboxPolicySUW.isChecked
            )
        }
        txtPolicySUW?.setOnClickListener { presenter.getSettings() }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.SIGN_UP_WELCOME_SCOPE)?.close()
    }

    override fun openSignUpSmsFragment(phone: String) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            SignUpSmsFragment.newInstance(phone),
            SignUpSmsFragment.TAG
        )
    }

    override fun showUserRoleData(dataList: ArrayList<String>) {
        val adapter = context?.let { ArrayAdapter<String>(it, R.layout.item_spinner, dataList) }
        spinnerTypeSUW.adapter = adapter
    }

    override fun openWebViewFragment(policy: String) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            WebViewFragment.newInstance(policy),
            WebViewFragment.TAG
        )
    }
}