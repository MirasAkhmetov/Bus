package com.thousand.bus.global.service

import com.thousand.bus.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ServerService {

    @FormUrlEncoded
    @POST(Endpoints.REGISTRATION)
    fun registration(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("role") role: String,
        @Field("device_token") deviceToken: String
    ): Completable

    @POST(Endpoints.AUTH)
    fun auth(): Single<User>

    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    fun signIn(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("device_token") deviceToken: String
    ): Single<UserResponse>

    @Multipart
    @POST(Endpoints.CONFIRMATION)
    fun confirmation(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part avatar: MultipartBody.Part?,
        @Part passportImage: MultipartBody.Part?,
        @Part passportImageBack: MultipartBody.Part?,
        @Part identityImage: MultipartBody.Part?,
        @Part identityImageBack: MultipartBody.Part?,
        @Part carImage: MultipartBody.Part?,
        @Part carImageSecond: MultipartBody.Part?,
        @Part carImageThird: MultipartBody.Part?
    ): Single<User>

    @Multipart
    @POST(Endpoints.ADD_BUS)
    fun addBus(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part passportImage: MultipartBody.Part?,
        @Part passportImageBack: MultipartBody.Part?,
        @Part carImage: MultipartBody.Part?,
        @Part carImageSecond: MultipartBody.Part?,
        @Part carImageThird: MultipartBody.Part?
    ): Single<User>

    @FormUrlEncoded
    @POST(Endpoints.SEND_FEEDBACK)
    fun sendFeedback(
        @Field("text") text: String?,
        @Field("criterion1") cleanliness: Float,
        @Field("criterion2") behaviour: Float,
        @Field("criterion3") convenience: Float,
        @Field("carId") carId: Int?
    ): Completable

    @GET(Endpoints.CAR_TYPE)
    fun getCarType(): Single<List<CarType>>

    @GET(Endpoints.CITY)
    fun getCities(): Single<List<City>>

    @GET(Endpoints.STATIONS)
    fun getStations(
        @Query("city_id") cityId: Int
    ): Single<List<Station>>

    @GET(Endpoints.TRAVEL_LIST)
    fun getTravelList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
//        @Query("from_city_id") fromCityId: Int?,
//        @Query("to_city_id") toCityId: Int?,
//        @Query("date") date: String?,
//        @Query("isBus") isBus: Int?,
//        @Query("baggage") baggage: Int?,
//        @Query("tv") tv: Int?,
//        @Query("conditioner") conditioner: Int?,
        @QueryMap map : MutableMap<String, Any>
    ): Single<TravelResponse>

    @GET(Endpoints.FEEDBACK_LIST)
    fun getFeedbackList(
        @Query("carId") id:Int?
    ): Single<Feedback>

    @GET(Endpoints.TRAVEL_SHOW)
    fun getTravel(
        @Query("travel_id") travelId: Int
    ): Single<TravelAndPlace>

    @POST(Endpoints.TRAVEL_CREATE)
    fun createTravel(
        @Body createTravel: CreateTravel
    ): Completable

    @GET(Endpoints.TRAVEL_UPCOMING)
    fun getUpcomingTravels(
        @Query("id") id: Int
    ): Single<List<Travel>>

    @GET(Endpoints.TRAVEL_HISTORY)
    fun getTravelHistory(): Single<List<Travel>>

    @GET(Endpoints.PASSENGERS)
    fun getPassengers(
        @Query("carId") id:Int?
    ): Single<List<Place>>


    @GET(Endpoints.CAR_LIST)
    fun getCarList(): Single<List<Car>>

    @FormUrlEncoded
    @POST(Endpoints.PLACE_EDIT)
    fun editPlace(
        @Field("travel_place_id") travelPlaceId: Int?,
        @Field("status") status: String?,
        @Field("passenger_id") passengerId: Int?
    ): Completable

    @GET(Endpoints.STATIONS_BETWEEN_CITY)
    fun getStationsBetweenCity(
        @Query("from_city_id") fromCityId: Int,
        @Query("to_city_id") toCityId: Int
    ): Single<List<Station>>

    @POST(Endpoints.BECOME_PASSENGER)
    fun becomePassenger(): Completable

    @POST(Endpoints.BECOME_DRIVER)
    fun becomeDriver(): Completable

    @Multipart
    @POST(Endpoints.PROFILE_EDIT)
    fun editProfile(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>?,
        @Part avatar: MultipartBody.Part?
    ): Completable

    @GET(Endpoints.TERMS_AND_POLICY)
    fun getTermsAndPolicy(): Single<TermAndPolicy>

    @FormUrlEncoded
    @POST(Endpoints.RESERVATION)
    fun placeReservation(
        @Field("travel_id") travelPlaceId: Int?,
        @FieldMap places: Map<String, String>
    ): Single<String>

    @Multipart
    @POST(Endpoints.ROLE_DRIVER)
    fun roleDriver(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>?,
        @Part avatar: MultipartBody.Part?,
        @Part passportImage: MultipartBody.Part?,
        @Part passportImageBack: MultipartBody.Part?,
        @Part identityImage: MultipartBody.Part?,
        @Part identityImageBack: MultipartBody.Part?,
        @Part carImage: MultipartBody.Part?,
        @Part carImageSecond: MultipartBody.Part?,
        @Part carImageThird: MultipartBody.Part?
    ): Completable

    @POST(Endpoints.ROLE_PASSENGER)
    fun rolePassenger(): Completable

    @GET(Endpoints.MY_TICKETS)
    fun getMyTickets(): Single<List<Ticket>>

    @FormUrlEncoded
    @POST(Endpoints.SMS_CONFIRM)
    fun smsConfirmation(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Single<UserResponse>

    @FormUrlEncoded
    @POST(Endpoints.PASSWORD_RESTORE_SEND_SMS)
    fun sendSmsForPasswordRestore(
        @Field("phone") phone: String
    ): Single<String>

    @FormUrlEncoded
    @POST(Endpoints.PASSWORD_RESTORE)
    fun restorePassword(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("code") code: String
    ): Single<User>

    @FormUrlEncoded
    @POST(Endpoints.TAKE_BACK_TICKET)
    fun takeBackTicket(
        @Field("place_id") placeId: Int
    ): Completable

    @FormUrlEncoded
    @POST(Endpoints.DELETE_TRAVEL)
    fun deleteUpcomingTravel(
        @Field("travel_id") travelId: Int
    ): Completable

    @GET(Endpoints.CLIENT_ORDER_HISTORY)
    fun getOrderHistories(): Single<List<OrderHistory>>

    @FormUrlEncoded
    @POST(Endpoints.LOGOUT)
    fun logout(
        @Field("token") token: String
    ): Completable
}
