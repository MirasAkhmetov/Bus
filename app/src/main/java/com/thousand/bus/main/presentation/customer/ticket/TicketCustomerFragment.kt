package com.thousand.bus.main.presentation.customer.ticket

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Ticket
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.TicketAdapter
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class TicketCustomerFragment : BaseFragment(), TicketCustomerView {

    companion object{

        val TAG = "TicketCustomerFragment"

        fun newInstance(): TicketCustomerFragment =
            TicketCustomerFragment()
    }

    @InjectPresenter
    lateinit var presenter: TicketCustomerPresenter


    @ProvidePresenter
    fun providePresenter(): TicketCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.TICKET_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(MainScope.TICKET_CUSTOMER_SCOPE, named(MainScope.TICKET_CUSTOMER_SCOPE)).get()
    }

    private val adapter = TicketAdapter{ presenter.onTakeBackItemClicked(it) }

    override val layoutRes: Int
        get() = R.layout.fragment_recycler

    override fun setUp(savedInstanceState: Bundle?) {
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener {  }
        }
        txtTitleToolbar?.text = getString(R.string.menu_tickets)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        presenter.onFirstInit()
        recycler.adapter = adapter
        context?.let {
            rootRecycler?.setBackgroundColor(
                ContextCompat.getColor(it, R.color.colorGreyLight)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.TICKET_CUSTOMER_SCOPE)?.close()
    }

    override fun showTickets(dataList: List<Ticket>) {
        adapter.submitData(dataList)
        txtEmptyList?.visible(dataList.isEmpty())
    }

}