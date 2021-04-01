package com.thousand.bus.main.presentation.driver.main

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Car
import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.*
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.AppConstants.INTENT_FILTER_MENU_BTN_CLICK
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.profile.ProfileFragment
import com.thousand.bus.main.presentation.customer.home.HomeCustomerFragment
import com.thousand.bus.main.presentation.customer.main.MainCustomerFragment
import com.thousand.bus.main.presentation.customer.ticket.TicketCustomerFragment
import com.thousand.bus.main.presentation.driver.add.AddBusFragment
import com.thousand.bus.main.presentation.driver.carlist.CarListDriverFragment
import com.thousand.bus.main.presentation.driver.history.HistoryDriverFragment
import com.thousand.bus.main.presentation.driver.home.HomeDriverFragment
import com.thousand.bus.main.presentation.driver.passenger.PassengerDriverFragment
import com.thousand.bus.main.presentation.driver.upcoming_travel.UpcomingTravelDriverFragment
import kotlinx.android.synthetic.main.fragment_driver_main.*
import kotlinx.android.synthetic.main.fragment_driver_main.drawerMainDriver
import kotlinx.android.synthetic.main.fragment_driver_main.view.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainDriverFragment : BaseFragment(), MainDriverView {

    companion object{

        val TAG = "MainDriverFragment"

        val BUNDLE_TYPE = "type"

        fun newInstance(type: String? = null): MainDriverFragment =
            MainDriverFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_TYPE, type)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: MainDriverPresenter

    @ProvidePresenter
    fun providePresenter(): MainDriverPresenter {
        getKoin().getScopeOrNull(MainScope.MAIN_DRIVER_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.MAIN_DRIVER_SCOPE, named(MainScope.MAIN_DRIVER_SCOPE))
        return scope.get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_main

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit(arguments?.getString(BUNDLE_TYPE))
        navDrawerMainDriver?.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawerDriverHome -> { presenter.onHomeBtnClicked() }
                R.id.drawerDriverListTravel -> { openHistoryDriverFragment() }
                R.id.drawerDriverMyPassengers -> { openPassengerDriverFragment() }
                R.id.drawerDriverAddBus-> {openAddBusFragment()}
                R.id.drawerDriverContact -> { presenter.onContactItemClicked() }
                R.id.drawerDriverUpcomingTravels-> { openUpcomingTravelDriverFragment() }
            }
            drawerMainDriver?.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        switchBecomePassenger?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.becomePassengerBtnClicked()
        }

        Log.d("Miras", "type data sdfsdfds ${arguments?.getString(BUNDLE_TYPE)}")
    }

    override fun onStart() {
        super.onStart()
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    menuBtnReceiver,
                    IntentFilter(INTENT_FILTER_MENU_BTN_CLICK)
                )
        }
    }

    override fun onStop() {
        super.onStop()
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .unregisterReceiver(menuBtnReceiver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.MAIN_DRIVER_SCOPE)?.close()
    }

    override fun openHomeDriverFragment(carId:Int, carTypeId : Int) {
        activity?.replaceFragment(
            R.id.container_main_driver,
            HomeDriverFragment.newInstance(carId = carId, carTypeId = carTypeId),
            HomeDriverFragment.TAG
        )
    }

    override fun openCarListDriverFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            CarListDriverFragment.newInstance(cardType = 1),
            CarListDriverFragment.TAG
        )
    }

    override fun openUpcomingTravelDriverFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            UpcomingTravelDriverFragment.newInstance(carFlag = 0, upcomingId = null),
            UpcomingTravelDriverFragment.TAG
        )
    }

    override fun openHistoryDriverFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            HistoryDriverFragment.newInstance(),
            HistoryDriverFragment.TAG
        )
    }

    override fun openPassengerDriverFragment(carId: Int) {
        activity?.replaceFragment(
            R.id.container_main_driver,
            PassengerDriverFragment.newInstance(carId = carId),
            PassengerDriverFragment.TAG
        )
    }

    override fun openAddBusFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            AddBusFragment.newInstance(),
            AddBusFragment.TAG
        )
    }


    private val menuBtnReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                INTENT_FILTER_MENU_BTN_CLICK -> {
                    drawerMainDriver?.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    override fun openMainCustomerFragment() {
        activity?.replaceFragment(
            R.id.container,
            MainCustomerFragment.newInstance(),
            MainCustomerFragment.TAG
        )
    }

    @SuppressLint("SetTextI18n")
    override fun showUserInfo(user: User) {
        navDrawerMainDriver
            ?.getHeaderView(0)
            ?.apply {
                findViewById<TextView>(R.id.txtNameHeader)
                    ?.text = "${user.name ?: ""} ${user.surname ?: ""}"
                findViewById<ImageView>(R.id.imgAvatarHeader)
                    ?.setCircleImageUrl(
                        user.avatar,
                        R.drawable.ic_43
                    )
                setOnClickListener {
                    closeDrawer()
                    openProfileFragment()
                }
            }
    }

    private fun closeDrawer(){
        drawerMainDriver?.closeDrawer(GravityCompat.START)
    }

    override fun openProfileFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            ProfileFragment.newInstance(AppConstants.ROLE_DRIVER),
            ProfileFragment.TAG
        )
    }

    override fun openPhoneActionView(phone: String) {
        activity?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://wa.me/$phone")
            )
        )
    }
}