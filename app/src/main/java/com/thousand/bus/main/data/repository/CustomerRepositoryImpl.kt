package com.thousand.bus.main.data.repository

import com.thousand.bus.entity.*
import com.thousand.bus.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single

class CustomerRepositoryImpl(
    private val serverService: ServerService
): CustomerRepository{

    override fun getTravelList(
        page: Int,
        limit: Int,
        fromCityId: Int?,
        toCityId: Int?,
        time: String?,
        isBus: Int?,
        baggage: Int?,
        tv: Int?,
        conditioner: Int?,
        filter: String?
    ): Single<TravelResponse> {
        val map = mutableMapOf<String,Any>()
        fromCityId?.let {
            map.put("from_city_id" , it)
        }
        toCityId?.let {
            map.put("to_city_id" , it)
        }
        time?.let {
            map.put("date" , it)
        }
        isBus?.let {
            map.put("isBus" , it)
        }
        baggage?.let {
            map.put("baggage" , it)
        }
        tv?.let {
            map.put("tv" , it)
        }
        conditioner?.let {
            map.put("conditioner" , it)
        }
        filter?.let {
            map.put("filter" , it)
        }
        return serverService.getTravelList(page, limit, map)
    }

    override fun sendFeedback(text: String?,
                              cleanliness: Float,
                              behaviour: Float,
                              convenience: Float,
                              carId : Int?): Completable =
        serverService.sendFeedback(text, cleanliness, behaviour, convenience, carId)

    override fun getFeedbackList(carId: Int?): Single<Feedback> =
        serverService.getFeedbackList(carId)

    override fun getFeedbackListPagination(page: Int, carId: Int?): Single<Feedback> =
        serverService.getFeedbackListPagination(page, carId)


    override fun getTravel(travelId: Int): Single<TravelAndPlace> =
        serverService.getTravel(travelId)

    override fun becomePassenger(): Completable =
        serverService.becomePassenger()

    override fun getPushToDriver(id: Int?, orderId: Int?): Single<PushToDriver> =
        serverService.getPushToDriver(id, orderId)


    override fun placeReservation(travelId: Int?, places: Map<String, String>): Single<Order> =
        serverService.placeReservation(travelId, places)



    override fun getMyTickets(): Single<List<Ticket>> =
        serverService.getMyTickets()

    override fun takeBackTicket(placeId: Int): Completable =
        serverService.takeBackTicket(placeId)

    override fun getOrderHistories(): Single<List<OrderHistory>> =
        serverService.getOrderHistories()

}