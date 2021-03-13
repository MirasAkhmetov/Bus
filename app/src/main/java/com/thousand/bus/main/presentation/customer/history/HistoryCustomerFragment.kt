package com.thousand.bus.main.presentation.customer.history

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.OrderHistory
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.OrderHistoryAdapter
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class HistoryCustomerFragment : BaseFragment(), HistoryCustomerView {

    companion object{

        val TAG = "HistoryCustomerFragment"

        fun newInstance(): HistoryCustomerFragment =
            HistoryCustomerFragment()
    }

    @InjectPresenter
    lateinit var presenter: HistoryCustomerPresenter

    private val historyAdapter = OrderHistoryAdapter{ }

    @ProvidePresenter
    fun providePresenter(): HistoryCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.HISTORY_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(MainScope.HISTORY_CUSTOMER_SCOPE, named(MainScope.HISTORY_CUSTOMER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_recycler

    override fun setUp(savedInstanceState: Bundle?) {
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener {  }
        }
        txtTitleToolbar?.text = getString(R.string.menu_list_travels)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        presenter.onFirstInit()
        recycler?.adapter = historyAdapter
        context?.let {
            rootRecycler?.setBackgroundColor(
                ContextCompat.getColor(it, R.color.colorGreyLight)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.HISTORY_CUSTOMER_SCOPE)?.close()
    }

    override fun showHistoryData(dataList: List<OrderHistory>) {
        historyAdapter.submitData(dataList)
        txtEmptyList?.visible(dataList.isEmpty())
    }

}