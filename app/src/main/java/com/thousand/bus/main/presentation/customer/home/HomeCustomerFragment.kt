package com.thousand.bus.main.presentation.customer.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.list.ListDialogFragment
import com.thousand.bus.main.presentation.customer.search_result.SearchResultCustomerFragment
import kotlinx.android.synthetic.main.fragment_customer_home.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeCustomerFragment : BaseFragment(), HomeCustomerView {

    companion object{

        val TAG = "HomeCustomerFragment"

        fun newInstance(): HomeCustomerFragment =
            HomeCustomerFragment()
    }

    @InjectPresenter
    lateinit var presenter: HomeCustomerPresenter

    @ProvidePresenter
    fun providePresenter(): HomeCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.HOME_CUSTOMER_SCOPE)?.close()
        return getKoin().createScope(
            MainScope.HOME_CUSTOMER_SCOPE,
            named(MainScope.HOME_CUSTOMER_SCOPE)
        ).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_home

    override fun setUp(savedInstanceState: Bundle?) {
        imgHomeToolbar?.apply {
            visible(true)
            setOnClickListener {
                context?.let {
                    LocalBroadcastManager.getInstance(it)
                        .sendBroadcast(
                            Intent(AppConstants.INTENT_FILTER_MENU_BTN_CLICK)
                        )
                }
            }
        }

        txtTitleToolbar?.text = getString(R.string.home)

        btnFindTicketCustomerHome?.setOnClickListener { presenter.onFindTicketBtnClicked() }
        txtFromCustomerHome?.setOnClickListener { presenter.onFromBtnClicked() }
        txtToCustomerHome?.setOnClickListener { presenter.onToBtnClicked() }
        txtDateCustomerHome?.setOnClickListener { showDataPickerDialog() }
//        imgPlusCustomerHome?.setOnClickListener { presenter.onPlusBtnClicked() }
//        imgMinusCustomerHome?.setOnClickListener { presenter.onMinusBtnClicked() }
        imgChangeFromTo?.setOnClickListener { presenter.onChangeFromToBtnClicked() }
        btnToday?.setOnClickListener {
            presenter.onTodayBtnClicked()
            btnToday.setBackgroundResource(R.drawable.background_blue_painted)
            btnToday.setTextColor(Color.parseColor("#ffffff"))
            btnTomorrow.setBackgroundResource(R.drawable.background_blue_rounded)
            btnTomorrow.setTextColor(Color.parseColor("#828282"))

        }
        btnTomorrow?.setOnClickListener{
            presenter.onTomorrowBtnClicked()
            btnTomorrow.setBackgroundResource(R.drawable.background_blue_painted)
            btnTomorrow.setTextColor(Color.parseColor("#ffffff"))
            btnToday.setBackgroundResource(R.drawable.background_blue_rounded)
            btnToday.setTextColor(Color.parseColor("#828282"))
        }
//        txtBaggageCustomerHome?.setOnClickListener { presenter.onComfortBtnClicked() }
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.HOME_CUSTOMER_SCOPE)?.close()
    }

    override fun openSearchResultCustomerFragment(travelListQuery: TravelListQuery) {
        activity?.addFragmentWithBackStack(
            R.id.container_main,
            SearchResultCustomerFragment.newInstance(travelListQuery),
            SearchResultCustomerFragment.TAG
        )
    }

    override fun openListDialogFragment(isMultiple: Boolean, dataList: List<ListItem>) {
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
        txtFromCustomerHome?.text = title
    }

    override fun showToTitle(title: String) {
        txtToCustomerHome?.text = title
    }

    override fun showDateTitle(title: String) {
        txtDateCustomerHome?.text = title
    }

    private fun showDataPickerDialog(){
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
            dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)?.setTextColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
            dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)?.setTextColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
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
            dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)?.setTextColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
            dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)?.setTextColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
        }
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        presenter.onDateSelected(year, month + 1, dayOfMonth)
        btnToday.setBackgroundResource(R.drawable.background_blue_rounded)
        btnToday.setTextColor(Color.parseColor("#828282"))
        btnTomorrow.setBackgroundResource(R.drawable.background_blue_rounded)
        btnTomorrow.setTextColor(Color.parseColor("#828282"))
        //showTimePickerDialog()
    }

    private val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        presenter.onTimeSelected(hourOfDay, minute)
    }

//    override fun showPlaceCount(title: String) {
//        txtPlaceCountCustomerHome?.text = title
//    }
//
//    override fun showComfortInfo(comfort: String) {
//        txtBaggageCustomerHome?.text = comfort
//    }

}