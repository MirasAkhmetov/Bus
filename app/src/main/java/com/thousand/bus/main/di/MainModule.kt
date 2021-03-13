package com.thousand.bus.main.di

import com.thousand.bus.entity.ListItem
import com.thousand.bus.entity.Place
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.main.presentation.common.list.ListDialogPresenter
import com.thousand.bus.main.presentation.activity.MainActivityPresenter
import com.thousand.bus.main.presentation.auth.customer.confirm.CustomerConfirmPresenter
import com.thousand.bus.main.presentation.auth.driver.confirm.DriverConfirmPresenter
import com.thousand.bus.main.presentation.auth.driver.wait.DriverConfirmWaitPresenter
import com.thousand.bus.main.presentation.auth.restore.password.PasswordRestorePresenter
import com.thousand.bus.main.presentation.auth.restore.phone.PhoneRestorePresenter
import com.thousand.bus.main.presentation.auth.sign_in.SignInPresenter
import com.thousand.bus.main.presentation.auth.sign_up.sms.SignUpSmsPresenter
import com.thousand.bus.main.presentation.auth.sign_up.welcome.SignUpWelcomePresenter
import com.thousand.bus.main.presentation.common.dialog.FeedbackDialogPresenter
import com.thousand.bus.main.presentation.common.profile.ProfilePresenter
import com.thousand.bus.main.presentation.customer.booking.BookingCustomerPresenter
import com.thousand.bus.main.presentation.customer.detail.OrderDetailCustomerPresenter
import com.thousand.bus.main.presentation.customer.edit.EditProfileCustomerPresenter
import com.thousand.bus.main.presentation.customer.feedback_list.FeedbackListCustomerPresenter
import com.thousand.bus.main.presentation.customer.history.HistoryCustomerPresenter
import com.thousand.bus.main.presentation.customer.home.HomeCustomerPresenter
import com.thousand.bus.main.presentation.customer.main.MainCustomerPresenter
import com.thousand.bus.main.presentation.customer.pre_payment.PrePaymentCustomerPresenter
import com.thousand.bus.main.presentation.customer.search_result.SearchResultCustomerPresenter
import com.thousand.bus.main.presentation.customer.ticket.TicketCustomerPresenter
import com.thousand.bus.main.presentation.driver.add.AddBusPresenter
import com.thousand.bus.main.presentation.driver.carlist.CarListDriverPresenter
import com.thousand.bus.main.presentation.driver.history.HistoryDriverPresenter
import com.thousand.bus.main.presentation.driver.home.HomeDriverPresenter
import com.thousand.bus.main.presentation.driver.main.MainDriverPresenter
import com.thousand.bus.main.presentation.driver.passenger.PassengerDriverPresenter
import com.thousand.bus.main.presentation.driver.places.PlacesDriverPresenter
import com.thousand.bus.main.presentation.driver.upcoming_travel.UpcomingTravelDriverPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {

    scope(named(MainScope.MAIN_ACTIVITY_SCOPE)){
        scoped { MainActivityPresenter(get()) }
    }

    scope(named(MainScope.SIGN_IN_SCOPE)){
        scoped { SignInPresenter(get()) }
    }

    scope(named(MainScope.SIGN_UP_WELCOME_SCOPE)){
        scoped { SignUpWelcomePresenter(get(), get(), get()) }
    }

    scope(named(MainScope.SIGN_UP_SMS_SCOPE)){
        scoped { (phone: String) -> SignUpSmsPresenter(phone, get(), get()) }
    }

    scope(named(MainScope.CUSTOMER_CONFIRM_SCOPE)){
        scoped { CustomerConfirmPresenter(get(), get()) }
    }

    scope(named(MainScope.DRIVER_CONFIRM_SCOPE)){
        scoped { (isRoleChange: Boolean) -> DriverConfirmPresenter(isRoleChange, get(), get(), get(), get()) }
    }

    scope(named(MainScope.DRIVER_CONFIRM_WAIT_SCOPE)){
        scoped { DriverConfirmWaitPresenter() }
    }

    scope(named(MainScope.HOME_CUSTOMER_SCOPE)){
        scoped { HomeCustomerPresenter(get(), get()) }
    }

    scope(named(MainScope.MAIN_CUSTOMER_SCOPE)){
        scoped { MainCustomerPresenter(get(), get()) }
    }

    scope(named(MainScope.SEARCH_RESULT_CUSTOMER_SCOPE)){
        scoped { (travelListQuery: TravelListQuery) -> SearchResultCustomerPresenter(travelListQuery, get()) }
    }

    scope(named(MainScope.BOOKING_CUSTOMER_SCOPE)){
        scoped { (travelId: Int) -> BookingCustomerPresenter(travelId, get()) }
    }

    scope(named(MainScope.ORDER_DETAIL_CUSTOMER_SCOPE)){
        scoped { (travelId: Int) -> OrderDetailCustomerPresenter(travelId, get()) }
    }

    scope(named(MainScope.LIST_DIALOG_SCOPE)){
        scoped { (isMultiple: Boolean, dataList: List<ListItem>) ->
            ListDialogPresenter(
                isMultiple,
                dataList
            )
        }
    }

    scope(named(MainScope.HOME_DRIVER_SCOPE)){
        scoped { HomeDriverPresenter(get(), get(), get()) }
    }

    scope(named(MainScope.MAIN_DRIVER_SCOPE)){
        scoped { MainDriverPresenter(get(), get(), get(), get()) }
    }

    scope(named(MainScope.UPCOMING_TRAVEL_DRIVER_SCOPE)){
        scoped { UpcomingTravelDriverPresenter( get()) }
    }

    scope(named(MainScope.CAR_LIST_DRIVER_SCOPE)){
        scoped { CarListDriverPresenter(get()) }
    }

    scope(named(MainScope.HISTORY_DRIVER_SCOPE)){
        scoped { HistoryDriverPresenter(get()) }
    }

    scope(named(MainScope.ADD_BUS_DRIVER_SCOPE)){
        scoped { AddBusPresenter(get(), get(), get(), get()) }
    }

    scope(named(MainScope.FEEDBACK_CUSTOMER_SCOPE)){
        scoped { FeedbackDialogPresenter(get()) }
    }

    scope(named(MainScope.PASSENGER_DRIVER_SCOPE)){
        scoped { PassengerDriverPresenter(get()) }
    }

    scope(named(MainScope.PLACES_DRIVER_SCOPE)){
        scoped { (travelId: Int) -> PlacesDriverPresenter(travelId, get(), get()) }
    }

    scope(named(MainScope.HISTORY_CUSTOMER_SCOPE)){
        scoped { HistoryCustomerPresenter(get()) }
    }

    scope(named(MainScope.TICKET_CUSTOMER_SCOPE)){
        scoped { TicketCustomerPresenter(get()) }
    }

    scope(named(MainScope.PROFILE_SCOPE)){
        scoped {(role: String) -> ProfilePresenter(role, get(), get()) }
    }

    scope(named(MainScope.EDIT_PROFILE_CUSTOMER_SCOPE)){
        scoped { EditProfileCustomerPresenter(get(), get()) }
    }

    scope(named(MainScope.PRE_PAYMENT_CUSTOMER_SCOPE)){
        scoped { (place: Place) -> PrePaymentCustomerPresenter(place, get(), get()) }
    }

    scope(named(MainScope.PASSWORD_RESTORE_SCOPE)){
        scoped { (phone: String) -> PasswordRestorePresenter(phone, get(), get()) }
    }

    scope(named(MainScope.PHONE_RESTORE_SCOPE)){
        scoped { PhoneRestorePresenter(get()) }
    }

    scope(named(MainScope.FEEDBACK_LIST_CUSTOMER_SCOPE)){
        scoped { FeedbackListCustomerPresenter(get()) }
    }
}

object MainScope{

    const val MAIN_ACTIVITY_SCOPE = "MainActivityScope"
    const val SIGN_IN_SCOPE = "SignInScope"
    const val SIGN_UP_WELCOME_SCOPE = "SignUpWelcomeScope"
    const val SIGN_UP_SMS_SCOPE = "SignUpSmsScope"
    const val CUSTOMER_CONFIRM_SCOPE = "CustomerConfirmScope"
    const val DRIVER_CONFIRM_SCOPE = "DriverConfirmScope"
    const val DRIVER_CONFIRM_WAIT_SCOPE = "DriverConfirmWaitScope"
    const val ADD_BUS_DRIVER_SCOPE = "DriverAddBusScope"
    const val FEEDBACK_CUSTOMER_SCOPE = "FeedbackScope"
    const val HOME_CUSTOMER_SCOPE = "HomeCustomerScope"
    const val MAIN_CUSTOMER_SCOPE = "MainCustomerScope"
    const val SEARCH_RESULT_CUSTOMER_SCOPE = "SearchResultCustomerScope"
    const val BOOKING_CUSTOMER_SCOPE = "BookingCustomerScope"
    const val ORDER_DETAIL_CUSTOMER_SCOPE = "OrderDetailCustomerScope"
    const val LIST_DIALOG_SCOPE = "ListDialogScope"
    const val HOME_DRIVER_SCOPE = "HomeDriverScope"
    const val MAIN_DRIVER_SCOPE = "HomeDriverScope"
    const val UPCOMING_TRAVEL_DRIVER_SCOPE = "UpcomingTravelDriverScope"
    const val CAR_LIST_DRIVER_SCOPE = "CarListDriverScope"
    const val HISTORY_DRIVER_SCOPE = "HistoryDriverScope"
    const val HISTORY_CUSTOMER_SCOPE = "HistoryCustomerScope"
    const val PASSENGER_DRIVER_SCOPE = "PassengerDriverScope"
    const val PLACES_DRIVER_SCOPE = "PlacesDriverScope"
    const val TICKET_CUSTOMER_SCOPE = "TicketCustomerScope"
    const val PROFILE_SCOPE = "ProfileScope"
    const val EDIT_PROFILE_CUSTOMER_SCOPE = "EditProfileCustomerScope"
    const val PRE_PAYMENT_CUSTOMER_SCOPE = "PrePaymentCustomerScope"
    const val PASSWORD_RESTORE_SCOPE = "PasswordRestoreScope"
    const val PHONE_RESTORE_SCOPE = "PhoneRestoreScope"
    const val FEEDBACK_LIST_CUSTOMER_SCOPE = "FeedbackListCustomerScope"

}