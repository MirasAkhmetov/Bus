package com.thousand.bus.main.presentation.customer.booking

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.BusSeat
import com.thousand.bus.entity.Place
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.getFormattedDateAndTime
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.BusSeatAdapter
import com.thousand.bus.main.presentation.customer.pre_payment.PrePaymentCustomerFragment
import kotlinx.android.synthetic.main.fragment_customer_booking.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class BookingCustomerFragment : BaseFragment(), BookingCustomerView {

    companion object{

        val TAG = "BookingCustomerFragment"

        private val BUNDLE_TRAVEL_ID = "travel_id"

        fun newInstance(travelId : Int): BookingCustomerFragment =
            BookingCustomerFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_TRAVEL_ID, travelId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: BookingCustomerPresenter

    private val adapter = BusSeatAdapter{
        presenter.onBusSeatItemSelected(it)
    }

    @ProvidePresenter
    fun providePresenter(): BookingCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.BOOKING_CUSTOMER_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.BOOKING_CUSTOMER_SCOPE, named(MainScope.BOOKING_CUSTOMER_SCOPE))
        val travelId = arguments?.getInt(BUNDLE_TRAVEL_ID)
        return scope.get { parametersOf(travelId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_booking

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.booking)
        recyclerCB?.adapter = adapter
        txtSumCB?.text = getString(R.string.price_value, "0")
        presenter.onFirstInit()
        btnBookingCB?.setOnClickListener { presenter.onPlaceReserveBtnClicked() }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.BOOKING_CUSTOMER_SCOPE)?.close()
    }

    override fun showBusSeatData(dataList: List<BusSeat>, typeBus: Int) {
        adapter.submitData(dataList, typeBus)
    }

    override fun showTotalPrice(price: Int) {
        txtSumCB?.text = getString(R.string.price_value, price.toString())
    }

//    override fun showTravelInfo(travel: Travel) {
//        txtTransportTypeCB?.text = travel.car?.name
//        txtDateTypeCB?.text = travel.destinationTime.getFormattedDateAndTime()
//    }

    override fun showSleepingSalon(show: Boolean) {
        txtBusType36CB.visible(show)
    }

    override fun openPrePaymentCustomerFragment(place: Place) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            PrePaymentCustomerFragment.newInstance(place),
            PrePaymentCustomerFragment.TAG
        )
    }

}