package com.thousand.bus.main.presentation.customer.detail

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_order_details.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class OrderDetailCustomerFragment : BaseFragment(), OrderDetailCustomerView, OnMapReadyCallback {

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

    private val adapter = StationAdapter()
    private var googleMap: GoogleMap? = null

    override fun setUp(savedInstanceState: Bundle?) {
        rootOD.setOnClickListener { activity?.onBackPressed() }
        recyclerStationOD?.adapter = adapter
        mapViewOD?.onCreate(savedInstanceState)
        mapViewOD?.onResume()
        mapViewOD?.getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.ORDER_DETAIL_CUSTOMER_SCOPE)?.close()
    }

    override fun showTravelInfo(travel: Travel) {
        txtTransportTypeOD?.text = travel.car?.name
        txtCityFromOD?.text = travel.from?.city
        txtTimeFromOD?.text = travel.departureTime.getFormattedTime()
        txtDateFromOD?.text = travel.departureTime.getFormattedDate()

        txtCityToOD?.text = travel.to?.city
        txtTimeToOD?.text = travel.destinationTime.getFormattedTime()
        txtDateToOD?.text = travel.destinationTime.getFormattedDate()

        txtPriceToOD?.text = getString(R.string.price_value, travel.maxPrice.toString())

        imgTelevisionOD?.visible(travel.car?.tv == 1)
        txtTelevisionOD?.visible(travel.car?.tv == 1)

        imgAirConditionOD?.visible(travel.car?.conditioner == 1)
        txtAirConditionOD?.visible(travel.car?.conditioner == 1)

        imgBaggageOD?.visible(travel.car?.baggage == 1)
        txtBaggageOD?.visible(travel.car?.baggage == 1)

        if (travel.from?.lat != null) {
            mapViewOD?.visible(true)

            googleMap?.apply {
                val latLng = LatLng(
                    travel.from.lat,
                    travel.from.lng ?: 0.0
                )
                addMarker(MarkerOptions().position(latLng))
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }

        if (travel.departureTime != null && travel.destinationTime != null){
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val fromDate = simpleDateFormat.parse(travel.departureTime)
            val toDate = simpleDateFormat.parse(travel.destinationTime)
            val totalTime = (toDate?.time ?: 0L) - (fromDate?.time ?: 0L)
            txtDuration?.text = getString(
                R.string.order_duration,
                TimeUnit.MILLISECONDS.toHours(totalTime),
                TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime))
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        presenter.onFirstInit()
    }

    override fun showStationDataList(dataList: List<Station>) {
        adapter.submitData(dataList)
    }

}