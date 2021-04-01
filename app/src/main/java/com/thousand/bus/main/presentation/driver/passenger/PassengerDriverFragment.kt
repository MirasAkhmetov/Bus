package com.thousand.bus.main.presentation.driver.passenger

import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Place
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.PassengerAdapter
import com.thousand.bus.main.presentation.driver.carlist.CarListDriverFragment
import com.thousand.bus.main.presentation.driver.upcoming_travel.UpcomingTravelDriverFragment
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PassengerDriverFragment : BaseFragment(), PassengerDriverView {

    companion object{

        val TAG = "PassengerDriverFragment"
        val BUNDLE_CAR_ID = "car_id"

        fun newInstance(carId:Int): PassengerDriverFragment =
            PassengerDriverFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_CAR_ID, carId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: PassengerDriverPresenter

    private val carId by lazy { requireArguments().getInt(BUNDLE_CAR_ID) }

    private val passengerAdapter = PassengerAdapter{ }

    @ProvidePresenter
    fun providePresenter(): PassengerDriverPresenter {
        getKoin().getScopeOrNull(MainScope.PASSENGER_DRIVER_SCOPE)?.close()
        return getKoin().createScope(MainScope.PASSENGER_DRIVER_SCOPE, named(MainScope.PASSENGER_DRIVER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_recycler_passenger

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.setCarID(carId = carId)
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener {  }
        }
        txtTitleToolbar?.text = getString(R.string.menu_my_passengers)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        presenter.onFirstInit()
        recycler?.adapter = passengerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.PASSENGER_DRIVER_SCOPE)?.close()
    }

    override fun showPassengerData(dataList: List<Place>) {
        passengerAdapter.submitData(dataList)
        txtEmptyList?.visible(dataList.isEmpty())
    }
    override fun openCarListDriverFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            CarListDriverFragment.newInstance(cardType = 3),
            CarListDriverFragment.TAG
        )
    }


}