package com.thousand.bus.main.presentation.customer.home

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.R
import com.thousand.bus.entity.City
import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.extension.getFormattedDate
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.data.interactor.ListInteractor
import kotlinx.android.synthetic.main.fragment_customer_home.*
import java.text.SimpleDateFormat
import java.util.*

@InjectViewState
class HomeCustomerPresenter(
    private var resourceManager: ResourceManager,
    private val listInteractor: ListInteractor
): BasePresenter<HomeCustomerView>(){

    private var cityList: List<City> = listOf()
    private val travelListQuery = TravelListQuery()

    fun onFirstInit(){
        getCities()
    }

    private fun getCities(){
        listInteractor.getCities()
            .subscribe(
                {
                    cityList = it
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun getStations(cityId: Int, requestCode: Int){
        viewState?.showProgressBar(true)
        listInteractor.getStations(cityId)
            .subscribe(
                {
                    val tempList: MutableList<ListItem> = mutableListOf()
                    it.forEach {
                        tempList.add(
                            ListItem(
                                id = it.id,
                                title = it.name,
                                requestCode = requestCode
                            )
                        )
                    }
                    viewState?.openListDialogFragment(false, tempList)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onFromBtnClicked(){
        val tempList: MutableList<ListItem> = mutableListOf()
        cityList.forEach {
            tempList.add(
                ListItem(
                    id = it.id,
                    title = it.name,
                    requestCode = AppConstants.RC_FROM_CITY
                )
            )
        }
        viewState?.openListDialogFragment(false, tempList)
    }

    fun onToBtnClicked(){
        val tempList: MutableList<ListItem> = mutableListOf()
        cityList.forEach {
            tempList.add(
                ListItem(
                    id = it.id,
                    title = it.name,
                    requestCode = AppConstants.RC_TO_CITY
                )
            )
        }
        viewState?.openListDialogFragment(false, tempList)
    }

    fun onListItemsSelected(listItems: List<ListItem>){
        if (listItems.isNotEmpty()){
            when(listItems.first().requestCode){
                AppConstants.RC_COMFORT -> {
                    travelListQuery.baggage = 0
                    travelListQuery.tv = 0
                    travelListQuery.conditioner = 0
                    var info = ""
                    listItems.forEach {
                        when(it.id){
                            AppConstants.COMFORT_BAGGAGE -> {
                                travelListQuery.baggage = 1
                                info += "${it.title}, "
                            }
                            AppConstants.COMFORT_TV -> {
                                travelListQuery.tv = 1
                                info += "${it.title}, "
                            }
                            AppConstants.COMFORT_AIR_CONDITION -> {
                                travelListQuery.conditioner = 1
                                info += "${it.title}, "
                            }
                        }
                    }
                    if (info.length > 2) info = info.substring(0, info.length - 2)
//                    viewState?.showComfortInfo(info)
                }
            }
        }
    }

    fun onListItemSelected(listItem: ListItem){
        when(listItem.requestCode){
            AppConstants.RC_FROM_CITY -> {
                travelListQuery.fromCityId = listItem.id
                travelListQuery.fromCity = listItem.title
                viewState?.showFromTitle("${travelListQuery.fromCity}")
            }
            AppConstants.RC_TO_CITY -> {
                travelListQuery.toCityId = listItem.id
                travelListQuery.toCity = listItem.title
                viewState?.showToTitle("${travelListQuery.toCity}")
            }

        }
    }

    fun onDateSelected(year: Int, month: Int, day: Int){
        travelListQuery.time = "$year-${String.format("%02d", month)}-${String.format("%02d", day)}"
        viewState?.showDateTitle(travelListQuery.time.getFormattedDate())
    }

    fun onTimeSelected(hour: Int, minute: Int){
        val date = travelListQuery.time
        travelListQuery.time = "$date ${String.format("%02d", hour)}:${String.format("%02d", minute)}:00"
        viewState?.showDateTitle(travelListQuery.time ?: "")
    }

    fun onTodayBtnClicked(){
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = df.format(c.time)
        travelListQuery.time = formattedDate
        viewState?.showDateTitle(formattedDate.getFormattedDate())

    }

    fun onTomorrowBtnClicked(){
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, 1)
        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = df.format(c.time)
        travelListQuery.time = formattedDate
        viewState?.showDateTitle(formattedDate.getFormattedDate())

    }


//    fun onPlusBtnClicked(){
//        travelListQuery.placeCount++
//        viewState?.showPlaceCount(travelListQuery.placeCount.toString())
//    }
//
//    fun onMinusBtnClicked(){
//        if (travelListQuery.placeCount > 1)
//            travelListQuery.placeCount--
//        viewState?.showPlaceCount(travelListQuery.placeCount.toString())
//    }

    fun onFindTicketBtnClicked(){
        if (travelListQuery.fromCityId == null || travelListQuery.toCityId == null || travelListQuery.time == null){
            viewState?.showMessage(resourceManager.getString(R.string.order_choose_stations))
            return
        }
        viewState?.openSearchResultCustomerFragment(travelListQuery)
//        viewState?.showComfortInfo("")
        travelListQuery.conditioner = 0
        travelListQuery.baggage = 0
        travelListQuery.tv = 0
    }

    fun onChangeFromToBtnClicked(){
        if (travelListQuery.fromCityId != null && travelListQuery.toCityId != null){
            val fromCityId = travelListQuery.fromCityId
            val fromCity = travelListQuery.fromCity

            val toCityId = travelListQuery.toCityId
            val toCity = travelListQuery.toCity

            travelListQuery.fromCityId = toCityId
            travelListQuery.fromCity = toCity
            travelListQuery.toCityId = fromCityId
            travelListQuery.toCity = fromCity

            viewState?.showToTitle("${travelListQuery.toCity}")
            viewState?.showFromTitle("${travelListQuery.fromCity} ")
        }
    }

    fun onComfortBtnClicked(){
        val listItems: MutableList<ListItem> = mutableListOf()
        listItems.add(
            ListItem(
                id = AppConstants.  COMFORT_BAGGAGE,
                title = resourceManager.getString(R.string.order_baggage),
                requestCode = AppConstants.RC_COMFORT
            )
        )
        listItems.add(
            ListItem(
                id = AppConstants.COMFORT_AIR_CONDITION,
                title = resourceManager.getString(R.string.order_air_condition),
                requestCode = AppConstants.RC_COMFORT
            )
        )
        listItems.add(
            ListItem(
                id = AppConstants.COMFORT_TV,
                title = resourceManager.getString(R.string.order_television),
                requestCode = AppConstants.RC_COMFORT
            )
        )
        viewState?.openListDialogFragment(true, listItems)
    }
}