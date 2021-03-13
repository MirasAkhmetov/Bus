package com.thousand.bus.main.presentation.customer.home

import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.base.BaseMvpView


interface HomeCustomerView : BaseMvpView {

    fun openSearchResultCustomerFragment(travelListQuery: TravelListQuery)

    fun openListDialogFragment(isMultiple: Boolean, dataList: List<ListItem>)

    fun showFromTitle(title: String)

    fun showToTitle(title: String)

    fun showDateTitle(title: String)

//    fun showPlaceCount(title: String)
//
//    fun showComfortInfo(comfort: String)

}