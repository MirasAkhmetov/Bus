package com.thousand.bus.main.presentation.driver.places

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.BusSeat
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.BusSeatAdapter
import kotlinx.android.synthetic.main.fragment_customer_booking.*
import kotlinx.android.synthetic.main.fragment_driver_places.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PlacesDriverFragment : BaseFragment(), PlacesDriverView {

    companion object{

        val TAG = "PlacesDriverFragment"

        private val BUNDLE_TRAVEL_ID = "travel_id"
        private val BUNDLE_CAR_ID = "car_id"

        fun newInstance(travelId : Int, carTypeId:Int): PlacesDriverFragment =
            PlacesDriverFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_TRAVEL_ID, travelId)
                    putInt(BUNDLE_CAR_ID, carTypeId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: PlacesDriverPresenter

    private val adapter = BusSeatAdapter {
        context?.let { it1 ->
            val dialog =  AlertDialog.Builder(it1)
                .setMessage(if (it.state == BusSeat.STATE_FREE) getString(R.string.are_you_sure) else getString(R.string.are_you_sure_cancel))
                .setPositiveButton(
                    getString(R.string.yes)
                ) { dialog, _ ->
                    presenter.busSeatBtnClicked(it)
                    dialog.dismiss()
                }
                .setNegativeButton(
                    getString(R.string.no)
                ){ dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it1, R.color.colorAccent))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it1, R.color.colorAccent))
        }
    }

    @ProvidePresenter
    fun providePresenter(): PlacesDriverPresenter {
        getKoin().getScopeOrNull(MainScope.PLACES_DRIVER_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.PLACES_DRIVER_SCOPE, named(MainScope.PLACES_DRIVER_SCOPE))
        val travelId = arguments?.getInt(BUNDLE_TRAVEL_ID)

        return scope.get { parametersOf(travelId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_driver_places

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.booking)
        recyclerDriverPlaces?.adapter = adapter
        presenter.onFirstInit(arguments?.getInt(BUNDLE_CAR_ID)!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.PLACES_DRIVER_SCOPE)?.close()
    }

    override fun showBusSeatData(dataList: List<BusSeat>, typeBus:Int) {
        adapter.submitData(dataList, typeBus)
        when (typeBus) {
            AppConstants.CAR_TYPE_50, AppConstants.CAR_TYPE_62, AppConstants.CAR_TYPE_36-> recyclerDriverPlaces?.setBackgroundResource(R.drawable.ic_schem_bus_36)
            else -> recyclerDriverPlaces?.setBackgroundResource(R.drawable.ic_schem_taxi)
        }
    }

}