package com.thousand.bus.main.presentation.driver.upcoming_travel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.extension.replaceFragmentWithBackStack
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.UpcomingTravelAdapter
import com.thousand.bus.main.presentation.driver.carlist.CarListDriverFragment
import com.thousand.bus.main.presentation.driver.places.PlacesDriverFragment
import kotlinx.android.synthetic.main.fragment_driver_upcoming_travel.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class UpcomingTravelDriverFragment : BaseFragment(), UpcomingTravelDriverView {

    companion object{

        val TAG = "UpcomingTravelDriverFragment"

        private val BUNDLE_UPCOMING_ID = "id"
        private val BUNDLE_CAR_FLAG = "car"

        fun newInstance(carFlag:Int, upcomingId:Int?): UpcomingTravelDriverFragment =
            UpcomingTravelDriverFragment().apply {
                arguments = Bundle().apply {
                    putInt(UpcomingTravelDriverFragment.BUNDLE_CAR_FLAG, carFlag)
                    if (upcomingId != null)
                        putInt(UpcomingTravelDriverFragment.BUNDLE_UPCOMING_ID,upcomingId)

                }
            }
    }

    @InjectPresenter
    lateinit var presenter: UpcomingTravelDriverPresenter

    private val travelAdapter = UpcomingTravelAdapter(
        { presenter.onTravelItemSelected(it) },
        { travel->
            context?.let {
           val dialog = AlertDialog.Builder(it)
               .setTitle(getString(R.string.delete_upcoming_travel))
               .setPositiveButton(getString(R.string.yes))
               { dialog, _ ->
                   presenter.onDeleteItemSelected(travel)
                   dialog.dismiss()
               }
               .setNegativeButton(getString(R.string.no))
               { dialog, _ ->
                   dialog.dismiss()
               }
               .create()
                dialog.show()
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
            }
        }
    )

    @ProvidePresenter
    fun providePresenter(): UpcomingTravelDriverPresenter {
        getKoin().getScopeOrNull(MainScope.UPCOMING_TRAVEL_DRIVER_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.UPCOMING_TRAVEL_DRIVER_SCOPE, named(MainScope.UPCOMING_TRAVEL_DRIVER_SCOPE))
        val upcoming_id = arguments?.getInt(BUNDLE_UPCOMING_ID)
        return scope.get { parametersOf(upcoming_id) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_upcoming_travel

    override fun setUp(savedInstanceState: Bundle?) {
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener {  }
        }
        txtTitleToolbar?.text = getString(R.string.menu_upcoming_travels)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        recyclerDUT?.adapter = travelAdapter
        presenter.onFirstInit(carFlag = arguments?.getInt(UpcomingTravelDriverFragment.BUNDLE_CAR_FLAG)!!,
            upcomindId = arguments?.getInt(UpcomingTravelDriverFragment.BUNDLE_UPCOMING_ID)!!
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.UPCOMING_TRAVEL_DRIVER_SCOPE)?.close()
    }

    override fun showTravelData(dataList: List<Travel>) {
        travelAdapter.submitData(dataList)
    }

    override fun openPlacesDriverFragment(travel:Travel) {
        activity?.addFragmentWithBackStack(
            R.id.container_main_driver,
            PlacesDriverFragment.newInstance(travel.id!!, travel.car?.carTypeId!!),
            PlacesDriverFragment.TAG
        )
    }

    override fun openCarListDriverFragment() {
        activity?.replaceFragment(
            R.id.container_main_driver,
            CarListDriverFragment.newInstance(cardType = 2),
            CarListDriverFragment.TAG
        )
    }

}