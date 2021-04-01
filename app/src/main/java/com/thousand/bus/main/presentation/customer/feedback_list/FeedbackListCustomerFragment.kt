package com.thousand.bus.main.presentation.customer.feedback_list

import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Car
import com.thousand.bus.entity.Feedback
import com.thousand.bus.entity.FeedbackListQuery
import com.thousand.bus.entity.Review
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.FeedbackAdapter
import com.thousand.bus.main.presentation.customer.detail.OrderDetailCustomerFragment
import kotlinx.android.synthetic.main.fragment_customer_feedback.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class FeedbackListCustomerFragment : BaseFragment(), FeedbackListCustomerView {

    companion object {

        val TAG = "FeedbackListCustomerFragment"
        val BUNDLE_CAR_ID = "carId"
        private val BUNDLE_CAR_STATE = "car_state"


        fun newInstance(carId: Int, carState : String): FeedbackListCustomerFragment =
            FeedbackListCustomerFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_CAR_ID, carId)
                    putString(BUNDLE_CAR_STATE, carState)
                }
            }
    }


    @InjectPresenter
    lateinit var presenter: FeedbackListCustomerPresenter

    private val carState by lazy { requireArguments().getString(BUNDLE_CAR_STATE) }

    @ProvidePresenter
    fun providePresenter(): FeedbackListCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)?.close()
        val scope =  getKoin().createScope(
            MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE,
            named(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)
        )
        val carId = arguments?.getInt(BUNDLE_CAR_ID)
        return scope.get { parametersOf(carId, FeedbackListQuery(page = 1)) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_feedback

    private val feedbackAdapter = FeedbackAdapter({ presenter.loadDataNextPage() })


    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.booking)

        presenter.onFirstInit()
        recyclerFeedback?.adapter = feedbackAdapter
        carNumberFeedback?.text = carState
    }


    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)?.close()
    }

    override fun showFeedbackList(feedback: Feedback) {
        Log.e("sdfsd", "review list + ${feedback}")

        recyclerFeedback?.visible(feedback.feedbackList?.data?.isNotEmpty())
        txtEmptyList?.visible(feedback.feedbackList?.data?.isEmpty())
        txtFeedbackCount?.text = "Все отзывы ${feedback.feedbackList?.total.toString()}"

        if (feedback.ratingInfo?.first()?.rating != null)
            ratingFeedback?.text =
                getString(R.string.number_feedback, feedback.ratingInfo?.first()?.rating)

        if (feedback.ratingInfo?.first()?.criterion1 != null)
            txtProgressOne?.text =
                getString(R.string.number_feedback, feedback.ratingInfo?.first()?.criterion1)

        if (feedback.ratingInfo?.first()?.criterion2 != null)
            txtProgressTwo?.text =
                getString(R.string.number_feedback, feedback.ratingInfo?.first()?.criterion2)

        if (feedback.ratingInfo?.first()?.criterion3 != null)
            txtProgressThree?.text =
                getString(R.string.number_feedback, feedback.ratingInfo?.first()?.criterion3)

        feedback.ratingInfo?.first()?.criterion1?.let {
            progressBarOne?.progress = it.toInt()
        }

//        progressBarTwo?.progress = feedback.ratingInfo?.first()?.criterion2!!.toInt()
//        progressBarThree?.progress = feedback.ratingInfo?.first()?.criterion3!!.toInt()

    }

    override fun showFeedbackData(dataList: List<Review>) {
        feedbackAdapter.submitData(dataList)
        txtEmptyList?.visible(dataList.isEmpty())
    }

}