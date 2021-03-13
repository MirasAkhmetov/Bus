package com.thousand.bus.main.presentation.auth.driver.wait

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.main.di.MainScope
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class DriverConfirmWaitFragment : BaseFragment(), DriverConfirmWaitView {

    companion object{

        val TAG = "DriverConfirmWaitFragment"

        fun newInstance(): DriverConfirmWaitFragment =
            DriverConfirmWaitFragment()
    }

    @InjectPresenter
    lateinit var presenter: DriverConfirmWaitPresenter

    @ProvidePresenter
    fun providePresenter(): DriverConfirmWaitPresenter {
        getKoin().getScopeOrNull(MainScope.DRIVER_CONFIRM_WAIT_SCOPE)?.close()
        return getKoin().createScope(MainScope.DRIVER_CONFIRM_WAIT_SCOPE, named(MainScope.DRIVER_CONFIRM_WAIT_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_confirm_wait

    override fun setUp(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.DRIVER_CONFIRM_WAIT_SCOPE)?.close()
    }
}