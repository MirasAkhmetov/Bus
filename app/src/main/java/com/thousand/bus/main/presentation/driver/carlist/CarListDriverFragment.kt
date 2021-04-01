package com.thousand.bus.main.presentation.driver.carlist

import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Car
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.CarListAdapter
import com.thousand.bus.main.presentation.driver.home.HomeDriverFragment
import com.thousand.bus.main.presentation.driver.passenger.PassengerDriverFragment
import com.thousand.bus.main.presentation.driver.upcoming_travel.UpcomingTravelDriverFragment
import kotlinx.android.synthetic.main.fragment_driver_car_list.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class CarListDriverFragment : BaseFragment(), CarListDriverView {

    companion object {

        val TAG = "CarListDriverFragment"
        private val BUNDLE_CARD_TYPE = "card_type"

        fun newInstance(cardType: Int): CarListDriverFragment = CarListDriverFragment().apply {
            arguments = Bundle().apply {
                putInt(CarListDriverFragment.BUNDLE_CARD_TYPE, cardType)
            }
        }
    }


    @InjectPresenter
    lateinit var presenter: CarListDriverPresenter

    private val carListAdapter =
        CarListAdapter { presenter.onCarListItemSelected(it, arguments!!.getInt(BUNDLE_CARD_TYPE)) }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_car_list


    @ProvidePresenter
    fun providePresenter(): CarListDriverPresenter {
        getKoin().getScopeOrNull(MainScope.CAR_LIST_DRIVER_SCOPE)?.close()
        return getKoin().createScope(
            MainScope.CAR_LIST_DRIVER_SCOPE,
            named(MainScope.CAR_LIST_DRIVER_SCOPE)
        ).get()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener { }
        }
        if (arguments!!.getInt(BUNDLE_CARD_TYPE) == 1)
            txtTitleToolbar?.text = getString(R.string.menu_home)
        else if (arguments!!.getInt(BUNDLE_CARD_TYPE) == 3)
            txtTitleToolbar?.text = getString(R.string.menu_my_passengers)
        else txtTitleToolbar?.text = getString(R.string.menu_upcoming_travels)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        recyclerCarList?.adapter = carListAdapter
        presenter.onFirstInit()
    }

    override fun showCarListData(dataList: List<Car>) {
        carListAdapter.submitData(dataList)
    }

    override fun openHomeDriverFragment(carId: Int, carTypeId: Int) {
        activity?.replaceFragment(
            R.id.container_main_driver,
            HomeDriverFragment.newInstance(carId = carId, carTypeId = carTypeId),
            HomeDriverFragment.TAG
        )
    }

    override fun openPassengerFragment(carId: Int) {
        activity?.replaceFragment(
            R.id.container_main_driver,
            PassengerDriverFragment.newInstance(carId = carId),
            PassengerDriverFragment.TAG
        )
    }


    override fun openUpcomingDriverFragment(upcoming_id: Int) {
        activity?.replaceFragment(
            R.id.container_main_driver,
            UpcomingTravelDriverFragment.newInstance(carFlag = 1, upcomingId = upcoming_id),
            UpcomingTravelDriverFragment.TAG
        )
    }


}