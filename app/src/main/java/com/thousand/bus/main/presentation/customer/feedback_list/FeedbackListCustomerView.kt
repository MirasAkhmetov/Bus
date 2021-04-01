package com.thousand.bus.main.presentation.customer.feedback_list

import com.thousand.bus.entity.Feedback
import com.thousand.bus.entity.Review
import com.thousand.bus.global.base.BaseMvpView

interface FeedbackListCustomerView : BaseMvpView {

    fun showFeedbackList(feedback: Feedback)

    fun showFeedbackData(dataList: List<Review>)

}