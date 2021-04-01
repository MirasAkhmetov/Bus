package com.thousand.bus.main.presentation.customer.booking

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.BusSeat
import com.thousand.bus.entity.Place
import com.thousand.bus.entity.TravelAndPlace
import com.thousand.bus.global.presentation.BasePresenter
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.data.interactor.CustomerInteractor

@InjectViewState
class BookingCustomerPresenter(
    private val travelId: Int,
    private val carId :Int,
    private val carState: String?,
    private val customerInteractor: CustomerInteractor
) : BasePresenter<BookingCustomerView>() {

    private var busSeatDataList: List<BusSeat> = listOf()
    private var busSeatYourList: List<String> = listOf()
    private var totalSum: Int = 0
    private var typeBus: Int? = null
    private var currentTravelAndPlace: TravelAndPlace? = null

//    private var carId :Int? = 0
//    private var carState: String? =null


    fun onFirstInit() {
        getTravel(travelId)
    }
//
//    fun setCarID(carId:Int, carState: String?) {
//
//        if (carId != 0 && carState!!.isNotEmpty()){
//            this.carId = carId
//            this.carState = carState
//        }
//    }

    fun onBusSeatItemSelected(busSeat: BusSeat) {
//        busSeatDataList.forEach {
//            if (it.state == BusSeat.STATE_YOUR)
//                it.state = BusSeat.STATE_FREE
//        }
        val selectedPlace = busSeatDataList.filter { it.state == BusSeat.STATE_YOUR }

        if (selectedPlace.size < 4) {
            if (busSeat.state == BusSeat.STATE_FREE) {
                busSeat.state = BusSeat.STATE_YOUR

                totalSum += busSeat.price ?: 0
                typeBus?.let { viewState?.showBusSeatData(busSeatDataList, it) }

            } else if (busSeat.state == BusSeat.STATE_YOUR) {
                busSeat.state = BusSeat.STATE_FREE
                totalSum -= busSeat.price ?: 0
                typeBus?.let { viewState?.showBusSeatData(busSeatDataList, it) }
            }

            viewState?.showTotalPrice(totalSum)
        } else {
            viewState.showMessage("Вы не можете выбрать больше 4 мест")
        }

//        if (busSeat.state == BusSeat.STATE_FREE){
//            busSeat.state = BusSeat.STATE_YOUR
//            viewState?.showBusSeatData(busSeatDataList)
//            totalSum = busSeat.price ?: 0
//
//        }
    }

    private fun getTravel(travelId: Int) {
        viewState?.showProgressBar(true)
        customerInteractor.getTravel(travelId)
            .subscribe(
                {
                    currentTravelAndPlace = it
                    typeBus = it.travel?.car?.carTypeId

                    when (it.travel?.car?.carTypeId) {
                        AppConstants.CAR_TYPE_62 -> busSeatDataList =
                            BusSeat.get62BusSeat().toList()
                        AppConstants.CAR_TYPE_50 -> busSeatDataList =
                            BusSeat.get50BusSeat().toList()
                        AppConstants.CAR_TYPE_ALPHARD -> busSeatDataList =
                            BusSeat.getAlphardSeat().toList()
                        AppConstants.CAR_TYPE_MINIVAN -> busSeatDataList =
                            BusSeat.getMinivanSeat().toList()
                        AppConstants.CAR_TYPE_TAXI -> busSeatDataList =
                            BusSeat.getTaxiSeat().toList()
                        AppConstants.CAR_TYPE_36 -> {
                            viewState.showSleepingSalon(true)
                            busSeatDataList = BusSeat.get36BusSeat().toList()
                        }
                    }
//                        it.travel?.let { viewState?.showTravelInfo(it) }
                    it.places?.forEach { place ->
                        busSeatDataList.forEach { seat ->

                                if (place.number == seat.id) {

                                    seat.placeId = place.number

                                    seat.price = place.price
                                    when (place.status) {
                                        AppConstants.STATUS_FREE -> seat.state = BusSeat.STATE_FREE
                                        AppConstants.STATUS_IN_PROCESS -> seat.state =
                                            BusSeat.STATE_IN_PROCESS
                                        AppConstants.STATUS_TAKE -> seat.state =
                                            BusSeat.STATE_NOT_FREE
                                    }
                                }


                        }
                    }
                    typeBus?.let { it1 -> viewState?.showBusSeatData(busSeatDataList, it1) }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onPlaceReserveBtnClicked() {
        val selectedPlace = busSeatDataList.filter { it.state == BusSeat.STATE_YOUR }

        val place = Place()
        place.reservedPlaceId = ""
        var price = 0

        when (currentTravelAndPlace?.travel?.car?.carTypeId) {


            AppConstants.CAR_TYPE_36 -> {

                selectedPlace.forEach {
                    price += it.price ?: 0
                    if (it.typeUpDown == 1) {
                        place.reservedPlaceId += "${it.upDownId}↓, "
                    } else {
                        place.reservedPlaceId += "${it.upDownId}↑, "
                    }
                }

            }
            else->{
                selectedPlace.forEach {
                    price += it.price ?: 0
                    place.reservedPlaceId += "${it.placeId}, "
                }
            }
        }

        place.price = price
        if (!place.reservedPlaceId.isNullOrEmpty()) place.reservedPlaceId =
            place.reservedPlaceId?.substring(0, (place.reservedPlaceId?.length ?: 2) - 2)
        place.fromCity = currentTravelAndPlace?.travel?.from?.city
        place.fromStation = currentTravelAndPlace?.travel?.from?.station
        place.toCity = currentTravelAndPlace?.travel?.to?.city
        place.toStation = currentTravelAndPlace?.travel?.to?.station
        place.departureDate = currentTravelAndPlace?.travel?.departureTime
        place.destinationDate = currentTravelAndPlace?.travel?.destinationTime
        place.freePlacesCount = currentTravelAndPlace?.travel?.countFreePlaces
        place.busSeats = selectedPlace
        place.travelId = travelId
        place.car = currentTravelAndPlace?.travel?.car
        viewState?.openPrePaymentCustomerFragment(place)
    }

    fun onDetailBtnClicked(tabItem : Int){
        viewState?.openViewPagerHandlerFragment(travelId, carId , carState , tabItem)
    }


}