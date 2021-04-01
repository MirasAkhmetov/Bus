package com.thousand.bus.main.presentation.customer.detail

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.thousand.bus.R
import com.thousand.bus.entity.Station
import com.thousand.bus.entity.Travel
import com.thousand.bus.entity.TravelStation
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.getFormattedDate
import com.thousand.bus.global.extension.getFormattedTime
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.customer.feedback_list.FeedbackListCustomerFragment
import com.thousand.bus.main.presentation.customer.pre_payment.PrePaymentCustomerFragment
import kotlinx.android.synthetic.main.fragment_order_details.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class OrderDetailCustomerFragment : BaseFragment(), OrderDetailCustomerView {

    companion object{

        val TAG = "DetailCustomerFragmentBottomSheet"

        private val BUNDLE_TRAVEL_ID = "travel_id"

        fun newInstance(travelId: Int): OrderDetailCustomerFragment =
            OrderDetailCustomerFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_TRAVEL_ID, travelId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: OrderDetailCustomerPresenter

    @ProvidePresenter
    fun providePresenter(): OrderDetailCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.ORDER_DETAIL_CUSTOMER_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.ORDER_DETAIL_CUSTOMER_SCOPE, named(MainScope.ORDER_DETAIL_CUSTOMER_SCOPE))
        val travelId = arguments?.getInt(BUNDLE_TRAVEL_ID)
        return scope.get { parametersOf(travelId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_order_details


    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.booking)

    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.ORDER_DETAIL_CUSTOMER_SCOPE)?.close()
    }

    override fun showTravelInfo(travel: Travel) {


        imgTelevisionOD?.visible(travel.car?.tv == 1)
        txtTelevisionOD?.visible(travel.car?.tv == 1)

        imgAirConditionOD?.visible(travel.car?.conditioner == 1)
        txtAirConditionOD?.visible(travel.car?.conditioner == 1)

        imgBaggageOD?.visible(travel.car?.baggage == 1)
        txtBaggageOD?.visible(travel.car?.baggage == 1)


    }



}