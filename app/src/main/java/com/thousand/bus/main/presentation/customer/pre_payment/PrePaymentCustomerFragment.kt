package com.thousand.bus.main.presentation.customer.pre_payment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.Place
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.*
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.customer.home.HomeCustomerFragment
import com.thousand.bus.main.presentation.customer.ticket.TicketCustomerFragment
import kotlinx.android.synthetic.main.fragment_customer_pre_payment.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class PrePaymentCustomerFragment : BaseFragment(), PrePaymentCustomerView {

    companion object{

        val TAG = "PrePaymentCustomerFragment"

        private val BUNDLE_PLACE = "place"

        fun newInstance(place: Place): PrePaymentCustomerFragment =
            PrePaymentCustomerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_PLACE, place)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: PrePaymentCustomerPresenter

    @ProvidePresenter
    fun providePresenter(): PrePaymentCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.PRE_PAYMENT_CUSTOMER_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.PRE_PAYMENT_CUSTOMER_SCOPE, named(MainScope.PRE_PAYMENT_CUSTOMER_SCOPE))
        val place = arguments?.getParcelable<Place>(BUNDLE_PLACE)
        return scope.get { parametersOf(place) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_pre_payment

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.order_details)
        imgBackToolbar?.setOnClickListener { activity?.onBackPressed() }
        btnBookCPP?.setOnClickListener {
            presenter.onWhatsappBtnClicked()
        }
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.PRE_PAYMENT_CUSTOMER_SCOPE)?.close()
    }

    override fun showPlaceInfo(place: Place) {
        val user = LocalStorage.getUser()
        imgAvatarCPP?.setCircleImageUrl(user?.avatar)
        txtNameCPP?.text = user?.name
      //  txtUserIdCPP?.text = user?.id?.toString()
        txtFromTimeCPP?.text = place.departureDate?.getFormattedTime()
        txtToTimeCPP?.text = place.destinationDate?.getFormattedTime()
        txtFromDateCPP?.text = place.departureDate?.getFormattedDateFromDateTime()
        txtToDateCPP?.text = place.destinationDate?.getFormattedDateFromDateTime()
        txtFromCityCPP?.text = place.fromCity
        txtFromStationCPP?.text = place.fromStation
        txtToCityCPP?.text = place.toCity
        txtToStationCPP?.text = place.toStation
        txtPlaceIdCPP?.text = place.reservedPlaceId
        txtSumCPP?.text = getString(R.string.price_value, place.price.toString())
        txtFreePlacesCPP?.text = place.car?.stateNumber

        val diff: Long = place.destinationDate.getFormattedDateTime() - place.departureDate.getFormattedDateTime()
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val time = "$hours час"
        txtDurationRoad.text = getString(R.string.duration_road, time)
    }

    override fun showWhatsappInfo(info: String) {
        txtNumberKaspi.text = info
      //  txtWhatsappPrePayment?.text = getString(R.string.transfer_number, info)
    }

    override fun showTimerInfo(minute: Int) {
        object : CountDownTimer(TimeUnit.MINUTES.toMillis(minute.toLong()), 1000) {
            @SuppressLint("StringFormatMatches")
            override fun onTick(millisUntilFinished: Long) {
                txtTimerPrePayment?.text = getString(
                        R.string.order_transfer_info_kaspi,
                        "${TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)}:${millisUntilFinished / 1000 % 60}"
                )
            }

            override fun onFinish() {
                txtTimerPrePayment?.text = getString(
                        R.string.order_transfer_info_kaspi,
                        "00:00"
                )
            }
        }.start()
    }

    override fun openActionView(phone: String, place: String, stateNumber: String) {
        activity?.startActivity(
                Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                                getString(
                                        R.string.whatsapp_text,
                                        phone.replace(" ", ""),
                                        place,
                                        stateNumber
                                )
                        )
                )
        )
    }

    override fun openHomeCustomerFragment() {
        activity?.supportFragmentManager
            ?.replaceFragment(
                    R.id.container_main,
                    TicketCustomerFragment.newInstance(),
                    TicketCustomerFragment.TAG
            )
    }

}