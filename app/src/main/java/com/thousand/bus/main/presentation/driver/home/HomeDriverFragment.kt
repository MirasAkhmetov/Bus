package com.thousand.bus.main.presentation.driver.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.*
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.*
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.replaceFragmentWithBackStack
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.presentation.common.list.ListDialogFragment
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.customer.search_result.SearchResultCustomerFragment
import com.thousand.bus.main.presentation.driver.carlist.CarListDriverFragment
import com.thousand.bus.main.presentation.driver.places.PlacesDriverFragment
import com.thousand.bus.main.presentation.driver.upcoming_travel.UpcomingTravelDriverFragment
import kotlinx.android.synthetic.main.fragment_driver_home.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.*
import kotlin.collections.ArrayList

class HomeDriverFragment : BaseFragment(), HomeDriverView {


    companion object{

        val TAG = "HomeDriverFragment"
        private val BUNDLE_CAR_ID = "car_id"
        fun newInstance( carId:Int): HomeDriverFragment=
            HomeDriverFragment().apply {
                arguments = Bundle().apply {
                    putInt(HomeDriverFragment.BUNDLE_CAR_ID, carId)
                }
            }

        }


    @InjectPresenter
    lateinit var presenter: HomeDriverPresenter

    private val priceAdapter = PriceAdapter{ presenter.onPriceItemDelete(it) }
    private val dateFromToAdapter = DateFromToAdapter(
        { presenter.onFromItemSelected(it) },
        { presenter.onToItemSelected(it) },
        { presenter.onDeleteItem(it) }
    )

    @ProvidePresenter
    fun providePresenter(): HomeDriverPresenter {
        getKoin().getScopeOrNull(MainScope.HOME_DRIVER_SCOPE)?.close()
         return getKoin().createScope(MainScope.HOME_DRIVER_SCOPE, named(MainScope.HOME_DRIVER_SCOPE)).get()
        }



    override val layoutRes: Int
        get() = R.layout.fragment_driver_home

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.setCarId(carId = arguments!!.getInt(
            HomeDriverFragment.BUNDLE_CAR_ID))

        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener {  }
        }
        txtTitleToolbar?.text = getString(R.string.home)
        imgHomeToolbar?.setOnClickListener {
            context?.let {
                LocalBroadcastManager.getInstance(it)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                    )
            }
        }
        txtFromDriverHome?.setOnClickListener { presenter.onFromBtnClicked() }
        txtToDriverHome?.setOnClickListener { presenter.onToBtnClicked() }
        txtDateFromDriverHome?.setOnClickListener { presenter.onDateFromBtnClicked() }
        txtDateToDriverHome?.setOnClickListener { presenter.onDateToBtnClicked() }
        btnNextDriverHome?.setOnClickListener { presenter.onNextBtnClicked() }
        imgChangeFromToDriver?.setOnClickListener { presenter.onChangeFromToBtnClicked() }
        btnReadyDriverHome?.setOnClickListener {
            presenter.onPriceReadyBtnClicked(
                edtFromDriverHome.text.toString(),
                edtToDriverHome.text.toString()
            )
            edtFromDriverHome.setText("")
            edtToDriverHome.setText("")
        }
        btnSeveralDayDriverHome?.setOnClickListener { presenter.onSeveralBtnClicked() }
        recyclerPlaceDriverHome?.adapter = priceAdapter
        recyclerDateDriverHome?.adapter = dateFromToAdapter
        edtFromDriverHome?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    val count = s.toString().toInt()
                    if (count > 99)
                        edtFromDriverHome?.setText("99")
                }
            }
        })

        edtToDriverHome?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    val count = s.toString().toInt()
                    if (count > 100)
                        edtToDriverHome?.setText("100")
                }
            }
        })
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.HOME_DRIVER_SCOPE)?.close()
    }

    override fun openSearchResultCustomerFragment(travelListQuery: TravelListQuery) {
        activity?.addFragmentWithBackStack(
            R.id.container_main,
            SearchResultCustomerFragment.newInstance(travelListQuery),
            SearchResultCustomerFragment.TAG
        )
    }

    override fun openListDialogFragment(dataList: List<ListItem>, isMultiple: Boolean) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(
                isMultiple = isMultiple,
                dataList = ArrayList(dataList),
                onItemSelectedListener = { presenter.onListItemSelected(it) },
                onItemsSelectedListener = { presenter.onListItemsSelected(it) }
            )
                .show(it, ListDialogFragment.TAG)
        }
    }

    override fun showFromTitle(title: String) {
        txtFromDriverHome?.text = title
    }

    override fun showToTitle(title: String) {
        txtToDriverHome?.text = title
    }

    override fun showFromDateTitle(title: String) {
        txtDateFromDriverHome?.text = title
    }

    override fun showToDateTitle(title: String) {
        txtDateToDriverHome?.text = title
    }

    override fun showIntermediateTitle(title: String) {

    }

    override fun showDataPickerDialog(){
        val calendar = Calendar.getInstance()
        context?.let {
           val dialog = DatePickerDialog(
                it,
                dateListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            dialog.datePicker.minDate = calendar.timeInMillis
            dialog.datePicker.maxDate = calendar.timeInMillis + (1000*60*60*24*7)
            dialog.show()
            dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
            dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
        }
    }

    override fun showDataPickerDialogTo(calendar: Calendar){
        val calendars = Calendar.getInstance()
        context?.let {
            val dialog = DatePickerDialog(
                it,
                dateListener,
                calendars[Calendar.YEAR],
                calendars[Calendar.MONTH],
                calendars[Calendar.DAY_OF_MONTH]
            )
            dialog.datePicker.minDate = calendar.timeInMillis
            dialog.datePicker.maxDate = calendars.timeInMillis + (1000*60*60*24*8)
            dialog.show()
            dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
            dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
        }
    }

    private fun showTimePickerDialog(){
        val calendar = Calendar.getInstance()
        context?.let {
            val dialog =TimePickerDialog(
                it,
                timeListener,
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                true
            )
            dialog.show()
            dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
            dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
        }
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        presenter.onDateSelected(year, month+1, dayOfMonth)
        showTimePickerDialog()
    }

    private val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        presenter.onTimeSelected(hourOfDay, minute)
    }

    override fun showPriceData(dataList: MutableList<PriceItem>) {
        priceAdapter.submitData(dataList)
        activity?.hideKeyboard()
    }

    override fun openUpcomingTravelDriverFragment(upcomindId:Int) {
        activity?.replaceFragmentWithBackStack(
            R.id.container_main_driver,
            UpcomingTravelDriverFragment.newInstance(carFlag = 0, upcomingId = upcomindId),
            UpcomingTravelDriverFragment.TAG
        )
    }

 
    override fun driverIsActive(isActive : Boolean) {
        txtFromDriverHome?.isEnabled = isActive
        txtToDriverHome?.isEnabled = isActive
        txtInactiveDriverHome?.visible(!isActive)
        txtDateFromDriverHome?.isEnabled = isActive
        txtDateToDriverHome?.isEnabled = isActive
        edtFromDriverHome?.isEnabled = isActive
        edtToDriverHome?.isEnabled = isActive
        btnReadyDriverHome?.isEnabled = isActive
        recyclerPlaceDriverHome?.isEnabled = isActive
        btnNextDriverHome?.isEnabled = isActive
        btnSeveralDayDriverHome?.isEnabled = isActive
    }

    override fun showDateFromToData(dataList: MutableList<FromTo>) {
        dateFromToAdapter.submitData(dataList)
    }

    override fun isCheckBusType(is36Seat: Boolean) {
        text21.visible(is36Seat)
    }

}