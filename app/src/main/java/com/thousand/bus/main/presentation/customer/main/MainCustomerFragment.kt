package com.thousand.bus.main.presentation.customer.main

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.*
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.driver.confirm.DriverConfirmFragment
import com.thousand.bus.main.presentation.common.profile.ProfileFragment
import com.thousand.bus.main.presentation.customer.history.HistoryCustomerFragment
import com.thousand.bus.main.presentation.customer.home.HomeCustomerFragment
import com.thousand.bus.main.presentation.customer.ticket.TicketCustomerFragment
import com.thousand.bus.main.presentation.driver.main.MainDriverFragment
import kotlinx.android.synthetic.main.fragment_customer_main.*
import kotlinx.android.synthetic.main.fragment_customer_main.view.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainCustomerFragment : BaseFragment(), MainCustomerView {

    companion object {

        val TAG = "MainCustomerFragment"
        val BUNDLE_TYPE = "type"

        fun newInstance(type: String? = null): MainCustomerFragment =
            MainCustomerFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_TYPE, type)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: MainCustomerPresenter

    @ProvidePresenter
    fun providePresenter(): MainCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.MAIN_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(MainScope.MAIN_CUSTOMER_SCOPE, named(MainScope.MAIN_CUSTOMER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_main

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit(arguments?.getString(BUNDLE_TYPE))
        navigationDrawerCustomer?.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawerCustomerHome -> { openHomeCustomerFragment() }
                R.id.drawerCustomerTravelList -> { openHistoryCustomerFragment() }
                R.id.drawerCustomerTicket -> { openMyTicketFragment() }
                R.id.drawerCustomerContact -> { presenter.onContactItemClicked() }
            }
            closeDrawer()
            return@setNavigationItemSelectedListener true
        }

        switchBecomeDriver?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.becomeDriverBtnClicked()
            drawerMainCustomer?.closeDrawer(GravityCompat.START)
        }

        Log.d("Miras", "type data 1234 ${arguments?.getString(MainDriverFragment.BUNDLE_TYPE)}")

    }

    override fun onStart() {
        super.onStart()
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    menuBtnReceiver,
                    IntentFilter(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
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
        getKoin().getScopeOrNull(MainScope.MAIN_CUSTOMER_SCOPE)?.close()
    }

    override fun openHomeCustomerFragment() {
        activity?.replaceFragment(
            R.id.container_main,
            HomeCustomerFragment.newInstance(),
            HomeCustomerFragment.TAG
        )
    }

    override fun openHistoryCustomerFragment() {
        activity?.replaceFragment(
            R.id.container_main,
            HistoryCustomerFragment.newInstance(),
            HistoryCustomerFragment.TAG
        )
    }

    override fun openMyTicketFragment() {
        activity?.replaceFragment(
            R.id.container_main,
            TicketCustomerFragment.newInstance(),
            TicketCustomerFragment.TAG
        )
    }

    private val menuBtnReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                AppConstants.INTENT_FILTER_MENU_BTN_CLICK -> {
                    drawerMainCustomer?.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    override fun openMainDriverFragment() {
        activity?.replaceFragment(
            R.id.container,
            MainDriverFragment.newInstance(),
            MainDriverFragment.TAG
        )
    }

    override fun openDriverConfirmFragment(isRoleChange: Boolean) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            DriverConfirmFragment.newInstance(isRoleChange),
            DriverConfirmFragment.TAG
        )
    }

    @SuppressLint("SetTextI18n")
    override fun showUserInfo(user: User) {
        navigationDrawerCustomer
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
        drawerMainCustomer?.closeDrawer(GravityCompat.START)
    }

    override fun openProfileFragment() {
        activity?.replaceFragmentWithBackStack(
            R.id.container,
            ProfileFragment.newInstance(AppConstants.ROLE_PASSENGER),
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