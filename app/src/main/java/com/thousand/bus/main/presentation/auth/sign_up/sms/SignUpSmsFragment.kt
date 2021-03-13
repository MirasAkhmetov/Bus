package com.thousand.bus.main.presentation.auth.sign_up.sms

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.customer.confirm.CustomerConfirmFragment
import com.thousand.bus.main.presentation.auth.driver.confirm.DriverConfirmFragment
import kotlinx.android.synthetic.main.fragment_sign_up_sms.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SignUpSmsFragment : BaseFragment(), SignUpSmsView {

    companion object{

        val TAG = "SignUpSmsFragment"
        
        private val BUNDLE_PHONE = "phone"

        fun newInstance(phone: String): SignUpSmsFragment =
            SignUpSmsFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_PHONE, phone)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: SignUpSmsPresenter

    @ProvidePresenter
    fun providePresenter(): SignUpSmsPresenter {
        getKoin().getScopeOrNull(MainScope.SIGN_UP_SMS_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.SIGN_UP_SMS_SCOPE, named(MainScope.SIGN_UP_SMS_SCOPE))
        val phone = arguments?.getString(BUNDLE_PHONE)
        return scope.get { parametersOf(phone) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_sign_up_sms

    private var pin: String = ""

    override fun setUp(savedInstanceState: Bundle?) {
        btnConfirmSUS?.setOnClickListener {
            presenter.onConfirmBtnClicked(pin)
        }
        pinSUS?.setOnPinEnteredListener {
            pin = it.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.SIGN_UP_SMS_SCOPE)?.close()
    }

    override fun openCustomerConfirmFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            CustomerConfirmFragment.newInstance(),
            CustomerConfirmFragment.TAG
        )
    }

    override fun openDriverConfirmFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            DriverConfirmFragment.newInstance(),
            DriverConfirmFragment.TAG
        )
    }

}