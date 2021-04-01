package com.thousand.bus.main.presentation.customer.search_result

import com.thousand.bus.entity.Travel
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.base.BaseMvpView


interface SearchResultCustomerView : BaseMvpView {

    fun showTravelData(dataList: List<Travel>)

    fun openBookingCustomerFragment(travelId: Int, carId: Int, carState : String)

    fun openImageDialog(travel: Travel)

    fun showTravelInfo(travelListQuery: TravelListQuery)

    fun setCountTab(count:Int, isBus:Int?)

}