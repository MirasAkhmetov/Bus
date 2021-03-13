package com.thousand.bus.main.presentation.driver.home

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bus.entity.FromTo
import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.PriceItem
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.base.BaseMvpView
import java.util.*

@StateStrategyType(OneExecutionStateStrategy::class)
interface HomeDriverView : BaseMvpView {

    fun openSearchResultCustomerFragment(travelListQuery: TravelListQuery)

    fun openListDialogFragment(dataList: List<ListItem>, isMultiple: Boolean = false)

    fun showFromTitle(title: String)

    fun showToTitle(title: String)

    fun showFromDateTitle(title: String)

    fun showToDateTitle(title: String)

    fun showIntermediateTitle(title: String)

    fun showPriceData(dataList: MutableList<PriceItem>)

    fun showDataPickerDialog()

    fun showDataPickerDialogTo(date:Calendar)

    fun openUpcomingTravelDriverFragment(upcomingId:Int)

    fun driverIsActive(isActive : Boolean)

    fun showDateFromToData(dataList: MutableList<FromTo>)

    fun isCheckBusType(is36Seat: Boolean)


}