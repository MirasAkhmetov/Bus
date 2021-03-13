package com.thousand.bus.main.presentation.customer.feedback_list

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Feedback
import com.thousand.bus.entity.Review
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.FeedbackAdapter
import com.thousand.bus.main.presentation.customer.history.HistoryCustomerPresenter
import kotlinx.android.synthetic.main.fragment_customer_feedback.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class FeedbackListCustomerFragment : BaseFragment(), FeedbackListCustomerView {


    @InjectPresenter
    lateinit var presenter: FeedbackListCustomerPresenter

    @ProvidePresenter
    fun providePresenter(): FeedbackListCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE, named(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_feedback

    private val feedbackAdapter = FeedbackAdapter()


    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.booking)

        presenter.onFirstInit()

        recyclerFeedback?.adapter = feedbackAdapter
    }


    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)?.close()
    }

    override fun showFeedbackList(dataList : List<Review>, feedback :Feedback) {
        feedbackAdapter.submitData(dataList)
        txtEmptyList?.visible(dataList.isEmpty())

        ratingFeedback?.text = feedback.ratingInfo?.rating.toString()
        txtProgressOne?.text = feedback.ratingInfo?.criterion1.toString()
        txtProgressTwo?.text = feedback.ratingInfo?.criterion2.toString()
        txtProgressThree?.text = feedback.ratingInfo?.criterion3.toString()
        txtFeedbackCount?.text = "Все отзывы ${feedback.feedbackList?.total.toString()}"

    }
}