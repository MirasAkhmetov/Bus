package com.thousand.bus.main.presentation.driver.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.TravelHistoryAdapter
import com.thousand.bus.main.presentation.common.dialog.FeedbackDialogFragment
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class HistoryDriverFragment : BaseFragment(), HistoryDriverView {

    companion object{

        val TAG = "HistoryDriverFragment"

        fun newInstance(): HistoryDriverFragment =
            HistoryDriverFragment()
    }

    @InjectPresenter
    lateinit var presenter: HistoryDriverPresenter

    private val historyAdapter = TravelHistoryAdapter{ openFeedback() }

    @ProvidePresenter
    fun providePresenter(): HistoryDriverPresenter {
        getKoin().getScopeOrNull(MainScope.HISTORY_DRIVER_SCOPE)?.close()
        return getKoin().createScope(MainScope.HISTORY_DRIVER_SCOPE, named(MainScope.HISTORY_DRIVER_SCOPE)).get()
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

    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.HISTORY_DRIVER_SCOPE)?.close()
    }

    override fun showHistoryData(dataList: List<Travel>) {
        historyAdapter.submitData(dataList)
        txtEmptyList?.visible(dataList.isEmpty())
    }

    private fun openFeedback(){
        var dialog = FeedbackDialogFragment()

        dialog.show(requireActivity().supportFragmentManager, "custom")
    }

}