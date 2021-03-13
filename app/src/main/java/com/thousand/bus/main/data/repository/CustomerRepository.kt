package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.*
import io.reactivex.Completable
import io.reactivex.Single

interface CustomerRepository {

    fun getTravelList(
        page: Int,
        limit: Int,
        fromCityId: Int?,
        toCityId: Int?,
        time: String?,
        isBus: Int?,
        baggage: Int?,
        tv: Int?,
        conditioner: Int?,
        filter : String?
    ): Single<TravelResponse>

    fun sendFeedback(
        text: String?,
        cleanliness: Float,
        behaviour: Float,
        convenience: Float,
        carId : Int?
    ): Completable

    fun getFeedbackList(carId: Int?) : Single<Feedback>

    fun getTravel(travelId: Int): Single<TravelAndPlace>

    fun becomePassenger(): Completable

    fun placeReservation(travelId: Int?, places: Map<String, String>): Single<String>

    fun getMyTickets(): Single<List<Ticket>>

    fun takeBackTicket(placeId: Int): Completable

    fun getOrderHistories(): Single<List<OrderHistory>>

}