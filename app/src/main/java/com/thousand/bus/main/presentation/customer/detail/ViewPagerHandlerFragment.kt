package com.thousand.bus.main.presentation.customer.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thousand.bus.R
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.presentation.customer.feedback_list.FeedbackListCustomerFragment
import kotlinx.android.synthetic.main.fragment_view_pager_handler.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ViewPagerHandlerFragment : Fragment() {

    companion object {

        val TAG = "ViewPagerHandlerFragment"

        private val BUNDLE_TRAVEL_ID = "travel_id"
        private val BUNDLE_CAR_ID = "carId"
        private val BUNDLE_CAR_STATE = "car_state"
        private val BUNDLE_TAB_ITEM = "tab_item"

        fun newInstance(travelId: Int, carId: Int?, carState: String?, tabItem : Int?): ViewPagerHandlerFragment =
            ViewPagerHandlerFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_TRAVEL_ID, travelId)
                    putInt(BUNDLE_CAR_ID, carId!!)
                    putString(BUNDLE_CAR_STATE, carState)
                    putInt(BUNDLE_TAB_ITEM, tabItem!!)
                }
            }
    }

    private val carId by lazy { requireArguments().getInt(BUNDLE_CAR_ID) }
    private val travelId by lazy { requireArguments().getInt(BUNDLE_TRAVEL_ID) }
    private val carState by lazy { requireArguments().getString(BUNDLE_CAR_STATE) }
    private val tabItem by lazy { requireArguments().getInt(BUNDLE_TAB_ITEM) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_view_pager_handler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = "Детали"

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(
            OrderDetailCustomerFragment.newInstance(travelId = travelId),
            getString(R.string.auth_comfort)
        )
        adapter.addFragment(FeedbackListCustomerFragment.newInstance(carId = carId, carState = carState!!), "Отзывы")
        adapter.addFragment(PoliticsFragment(), "Политика")
        viewPager.adapter = adapter
        viewPager.currentItem = tabItem
        tabLayoutComfort.setupWithViewPager(viewPager)

    }


}