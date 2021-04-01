package com.thousand.bus.main.data.interactor

import com.thousand.bus.entity.*
import com.thousand.bus.global.system.SchedulersProvider
import com.thousand.bus.main.data.repository.CustomerRepository
import io.reactivex.Completable
import io.reactivex.Single

class CustomerInteractor(
    private val customerRepository: CustomerRepository,
    private val schedulersProvider: SchedulersProvider
){

    fun getTravelList(travelListQuery: TravelListQuery): Single<List<Travel>?> =
        customerRepository.getTravelList(
            page = travelListQuery.page,
            limit = travelListQuery.limit,
            fromCityId = travelListQuery.fromCityId,
            toCityId = travelListQuery.toCityId,
            time = travelListQuery.time,
            isBus = travelListQuery.isBus,
            baggage = travelListQuery.baggage,
            tv = travelListQuery.tv,
            conditioner = travelListQuery.conditioner,
            filter = travelListQuery.filter
        ).map { it.data }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTravelCount(travelListQuery: TravelListQuery): Single<TravelResponse?> =
        customerRepository.getTravelList(
            page = travelListQuery.page,
            limit = travelListQuery.limit,
            fromCityId = travelListQuery.fromCityId,
            toCityId = travelListQuery.toCityId,
            time = travelListQuery.time,
            isBus = travelListQuery.isBus,
            baggage = travelListQuery.baggage,
            tv = travelListQuery.tv,
            conditioner = travelListQuery.conditioner,
            filter = travelListQuery.filter
        ).map { it }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun sendFeedback(
        text: String?,
        cleanliness: Float,
        behaviour: Float,
        convenience: Float,
        carId : Int?
    ): Completable =
        customerRepository.sendFeedback(text, cleanliness, behaviour, convenience, carId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getFeedbackList(carId: Int?): Single<Feedback> =
        customerRepository.getFeedbackList(carId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getFeedbackListPagination(feedbackListQuery : FeedbackListQuery, carId: Int? ) : Single<List<Review>?> =
        customerRepository.getFeedbackListPagination(
            page = feedbackListQuery.page,
            carId = carId
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui()).map { it.feedbackList?.data }

    fun getTravel(travelId: Int): Single<TravelAndPlace> =
        customerRepository.getTravel(travelId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun becomePassenger(): Completable =
        customerRepository.becomePassenger()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun placeReservation(travelId: Int?, places: Map<String, String>): Single<Order> =
        customerRepository.placeReservation(travelId, places)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getPushToDriver(id : Int?, orderId: Int?) : Single<PushToDriver> =
        customerRepository.getPushToDriver(id, orderId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())


    fun getMyTickets(): Single<List<Ticket>> =
        customerRepository.getMyTickets()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun takeBackTicket(placeId: Int): Completable =
        customerRepository.takeBackTicket(placeId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getOrderHistories(): Single<List<OrderHistory>> =
        customerRepository.getOrderHistories()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

}