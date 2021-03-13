package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Place
import com.thousand.bus.global.extension.getFormattedDateAndTime
import com.thousand.bus.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_my_ticket.view.*
import kotlinx.android.synthetic.main.item_passenger.view.*

class PassengerAdapter(val OnItemSelectedListener: (Place) -> Unit): RecyclerView.Adapter<PassengerAdapter.MyViewHolder>(){

    private var dataList: List<Place> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_passenger, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Place>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(place: Place){
            itemView.apply {
                txtCityFromPassenger?.text = place.from?.city
                txtStationFromPassenger?.text = place.from?.station
//                txtTimeFromPassenger?.text = place.departureTime?.getFormattedTime()
//                txtDateFromPassenger?.text = place.departureTime?.getFormattedDate()
                txtCityToPassenger?.text = place.to?.city
                txtStationToPassenger?.text = place.to?.station
//                txtTimeToPassenger?.text = place.destinationTime?.getFormattedTime()
//                txtDateToPassenger?.text = place.destinationTime?.getFormattedDate()
                txtPricePassenger?.text = ""
//                txtBookingDatePassenger?.text =

                when (place.car?.carTypeId) {
                    AppConstants.CAR_TYPE_50 -> { txtNumberPassenger?.text = place.number.toString() }
                    AppConstants.CAR_TYPE_ALPHARD -> { txtNumberPassenger?.text = place.number.toString() }
                    AppConstants.CAR_TYPE_MINIVAN -> { txtNumberPassenger?.text = place.number.toString() }
                    AppConstants.CAR_TYPE_TAXI -> { txtNumberPassenger?.text = place.number.toString() }
                    AppConstants.CAR_TYPE_36 -> {

                        place.number?.let {
                            if (it in 17..32) {
                                txtNumberPassenger?.text = "${it.minus(16)}↑"
                            } else {

                                if (it == 33 || it == 34) {
                                    txtNumberPassenger?.text = "0↑"
                                } else if (it == 35 || it == 36) {
                                    txtNumberPassenger?.text = "0↓"
                                } else {
                                    txtNumberPassenger?.text = "$it↓"
                                }
                            }
                        }
                    }
                }



                txtStateNumberPassenger?.text = place.passenger?.phone
                txtPricePassenger?.text = context.getString(R.string.price_value, place.price.toString())
                //setOnClickListener { OnItemSelectedListener.invoke(travel) }
                txtBookingDatePassenger?.text = place.bookingTime.getFormattedDateAndTime()
                txtStateNumberPassenger?.setOnClickListener {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel: ${place.passenger?.phone}")
                        )
                    )
                }
            }
        }

    }

}