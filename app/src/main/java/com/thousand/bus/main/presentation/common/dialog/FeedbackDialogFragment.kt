package com.thousand.bus.main.presentation.common.dialog

import android.os.Bundle
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseDialogFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_dialog.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class FeedbackDialogFragment : BaseDialogFragment(), FeedbackDialogView {

    companion object {
        val TAG = "FeedbackDialogFragment"
        val BUNDLE_CAR_ID = "car_id"

        fun newInstance(carId: Int): FeedbackDialogFragment =
            FeedbackDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_CAR_ID, carId)
                }
            }
    }


    @InjectPresenter
    lateinit var presenter: FeedbackDialogPresenter

    private val carId by lazy { requireArguments().getInt(BUNDLE_CAR_ID) }

    @ProvidePresenter
    fun providePresenter(): FeedbackDialogPresenter {
        getKoin().getScopeOrNull(MainScope.FEEDBACK_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(
            MainScope.FEEDBACK_CUSTOMER_SCOPE,
            named(MainScope.FEEDBACK_CUSTOMER_SCOPE)
        ).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_dialog

    override fun setUp(savedInstanceState: Bundle?) {

        presenter.setCarID(carId = carId)

        reviewClose.setOnClickListener { dismiss() }


        reviewRatingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->

            val rate = rating.toInt()

            when (rate) {
                5 -> {
                    reviewThree.visible(false)
                    reviewClean.rating = 5F
                    reviewAction.rating = 5F
                    reviewSpeed.rating = 5F
                }

                else -> {
                    reviewThree.visible(true)
                    reviewClean.rating
                    reviewAction.rating
                    reviewSpeed.rating

                }

            }
        }

        reviewSendButton.setOnClickListener {
            presenter.onSubmitBtnClicked(
                text = editText.text.toString(),
                cleanliness = reviewClean.rating,
                behaviour = reviewAction.rating,
                convenience = reviewSpeed.rating
            )
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun closeDialog() {
        dismiss()
    }


}