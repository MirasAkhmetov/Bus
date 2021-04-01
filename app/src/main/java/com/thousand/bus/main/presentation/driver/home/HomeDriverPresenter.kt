package com.thousand.bus.main.presentation.driver.home

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.R
import com.thousand.bus.entity.*
import com.thousand.bus.global.extension.getErrorMessage
import com.thousand.bus.global.extension.getFormattedDateAndTime
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.AppConstants.RC_INTERMEDIATE_STATION
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.data.interactor.DriverInteractor
import com.thousand.bus.main.data.interactor.ListInteractor
import org.koin.ext.isInt
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.DataFormatException

@InjectViewState
class HomeDriverPresenter(
    private val resourceManager: ResourceManager,
    private val listInteractor: ListInteractor,
    private val driverInteractor: DriverInteractor
): BasePresenter<HomeDriverView>(){

    private val DATE_FROM = 1
    private val DATE_TO = 2
    private val DATE_ADAPTER_FROM = 3
    private val DATE_ADAPTER_TO = 4

    private var calendar = Calendar.getInstance()

    private var cityList: List<City> = listOf()
    private var createTravel = CreateTravel()
    private var dateSelected = 0
    private var date: String = ""
    private var currentFromTo: FromTo? = null

    fun onFirstInit(){

//        when (LocalStorage.getUser()?.car?.carTypeId) {
//
//            AppConstants.CAR_TYPE_36 -> viewState.isCheckBusType(true)
//            else -> viewState.isCheckBusType(false)
//        }

        when(LocalStorage.getUser()?.confirmation){
            AppConstants.CONFIRMATION_CONFIRM -> viewState?.driverIsActive(true)
            AppConstants.CONFIRMATION_REJECT -> viewState?.driverIsActive(false)
            AppConstants.CONFIRMATION_WAITING -> viewState?.driverIsActive(false)
            null -> viewState?.driverIsActive(false)
            else -> viewState?.driverIsActive(true)
        }

        getCities()
        viewState?.showDateFromToData(createTravel.times)
    }

    private fun getCities() {
        viewState?.showProgressBar(true)
        listInteractor.getCities()
            .subscribe(
                {
                    cityList = it
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
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
                    viewState?.openListDialogFragment(tempList)
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
        viewState?.openListDialogFragment(tempList)
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
        viewState?.openListDialogFragment(tempList)
    }

    fun onIntermediateStationBtnClicked(){
        if (createTravel.fromCityId != null || createTravel.toCityId != null){
            viewState?.showProgressBar(true)
            listInteractor.getStationsBetweenCity(createTravel.fromCityId ?: 0, createTravel.toCityId ?: 0)
                .subscribe(
                    {
                        val tempList: MutableList<ListItem> = mutableListOf()
                        it.forEach {
                            tempList.add(
                                ListItem(
                                    id = it.station_id,
                                    title = it.stations,
                                    requestCode = RC_INTERMEDIATE_STATION
                                )
                            )
                        }
                        viewState?.openListDialogFragment(tempList, true)
                        viewState?.showProgressBar(false)
                    },
                    {
                        it.printStackTrace()
                        viewState?.showProgressBar(false)
                    }
                ).connect()
        }else{
            viewState?.showMessage(resourceManager.getString(R.string.order_choose_both_cities))
        }
    }

    fun onListItemSelected(listItem: ListItem){
        when(listItem.requestCode){
            AppConstants.RC_FROM_CITY -> {
                createTravel.fromCity = listItem.title
                getStations(listItem.id ?: 0, AppConstants.RC_FROM_STATION)
                createTravel.fromCityId = listItem.id
            }
            AppConstants.RC_TO_CITY -> {
                createTravel.toCity = listItem.title
                getStations(listItem.id ?: 0, AppConstants.RC_TO_STATION)
                createTravel.toCityId = listItem.id
            }
            AppConstants.RC_FROM_STATION -> {
                createTravel.fromStationId = listItem.id
                createTravel.fromStation = listItem.title
                viewState?.showFromTitle("${createTravel.fromCity} - ${createTravel.fromStation}")
                createTravel.fromStationId = listItem.id
            }
            AppConstants.RC_TO_STATION -> {
                createTravel.toStationId = listItem.id
                createTravel.toStation = listItem.title
                viewState?.showToTitle("${createTravel.toCity} - ${createTravel.toStation}")
                createTravel.toStationId = listItem.id
            }
        }
    }

    fun onListItemsSelected(listItems: List<ListItem>){
        if (listItems.isNotEmpty()){
            when(listItems.first().requestCode){
                RC_INTERMEDIATE_STATION -> {
                    createTravel.stations?.clear()
                    var title = ""
                    listItems.forEach {
                        it.id?.let {
                            createTravel.stations?.add(it)
                        }
                        title += "${it.title} , "
                    }

                    Timber.d("stations_id ${createTravel.stations}")

                    if (title.isNotEmpty()) title = title.substring(0, title.length - 2)
                    viewState?.showIntermediateTitle(title)
                }
            }
        }
    }


    fun onDateFromBtnClicked(){
        dateSelected = DATE_FROM
        viewState?.showDataPickerDialog()
    }

    fun onDateToBtnClicked(){
        if (date.isEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.fill_from_date))
            return
        }

        dateSelected = DATE_TO
        viewState?.showDataPickerDialogTo(calendar)

    }

    fun onDateSelected(year: Int, month: Int, day: Int){
        date = "$year-${String.format("%02d", month)}-${String.format("%02d", day)}"
        calendar = Calendar.getInstance()
        calendar.set(year,(month-1),day)
    }

    fun onTimeSelected(hour: Int, minute: Int){
        val time = "$date ${String.format("%02d", hour)}:${String.format("%02d", minute)}:00"

        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:sss", Locale.getDefault())
            val date = simpleDateFormat.parse(time)
            val currentDate = Calendar.getInstance().time
            if (date?.time ?: 0 < currentDate.time){
                viewState?.showMessage(resourceManager.getString(R.string.wrong_date))
                return
            }
        } catch (e : DataFormatException){
            e.printStackTrace()
        }

        when(dateSelected){
            DATE_FROM -> {
                createTravel.departureTime = time
                viewState?.showFromDateTitle(time.getFormattedDateAndTime())
            }
            DATE_TO -> {
                createTravel.destinationTime = time
                viewState?.showToDateTitle(time.getFormattedDateAndTime())
            }
            DATE_ADAPTER_FROM -> {
                currentFromTo?.departureTime = time
                viewState?.showDateFromToData(createTravel.times)
            }
            DATE_ADAPTER_TO -> {
                currentFromTo?.destinationTime = time
                viewState?.showDateFromToData(createTravel.times)
            }
        }
    }

    fun onPriceReadyBtnClicked(from: String, to: String){
        if (from.isInt() && to.isInt()){
            createTravel.placePrices
                .add(
                    PriceItem(
                        from = from.toInt(),
                        to = to.toInt()
                    )
                )
            viewState?.showPriceData(createTravel.placePrices)
        }
    }

    fun onNextBtnClicked(){

        if (createTravel.fromStationId == null ||
                createTravel.toStationId == null ||
                createTravel.departureTime == null ||
                createTravel.destinationTime == null ||
                createTravel.placePrices.isNullOrEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.fill_all_field))
            return
        }

        Log.d("LOG_STATTIONS", createTravel.stations?.toString())

        viewState?.showProgressBar(true)
        driverInteractor.createTravel(createTravel)
            .subscribe(
                {
                    viewState?.openUpcomingTravelDriverFragment(createTravel.carId!!)

                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                    viewState?.showMessage(it.getErrorMessage())
                }
            ).connect()
    }

    fun onSeveralBtnClicked(){
        createTravel.times.add(FromTo())
        viewState?.showDateFromToData(createTravel.times)
    }

    fun onFromItemSelected(fromTo: FromTo){
        currentFromTo = fromTo
        dateSelected = DATE_ADAPTER_FROM
        viewState?.showDataPickerDialog()
    }

    fun onToItemSelected(fromTo: FromTo){
        currentFromTo = fromTo
        dateSelected = DATE_ADAPTER_TO
        viewState?.showDataPickerDialog()
    }

    fun onDeleteItem(position: Int){
        createTravel.times.removeAt(position)
        viewState?.showDateFromToData(createTravel.times)
    }

    fun onPriceItemDelete(position: Int){
        createTravel.placePrices.removeAt(position)
        viewState?.showPriceData(createTravel.placePrices)
    }

    fun onChangeFromToBtnClicked(){
        if (createTravel.fromStationId != null && createTravel.toStationId != null){
            val fromStationId = createTravel.fromStationId
            val fromStation = createTravel.fromStation
            val fromCity = createTravel.fromCity

            val toStationId = createTravel.toStationId
            val toStation = createTravel.toStation
            val toCity = createTravel.toCity

            createTravel.fromStationId = toStationId
            createTravel.fromStation = toStation
            createTravel.fromCity = toCity
            createTravel.toStationId = fromStationId
            createTravel.toStation = fromStation
            createTravel.toCity = fromCity

            viewState?.showToTitle("${createTravel.toCity} - ${createTravel.toStation}")
            viewState?.showFromTitle("${createTravel.fromCity} - ${createTravel.fromStation}")
        }
    }
    fun setCarId(carId:Int) {
        createTravel.carId = carId
    }
}