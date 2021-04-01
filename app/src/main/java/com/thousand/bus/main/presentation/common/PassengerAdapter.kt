package com.thousand.bus.main.presentation.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Place
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.presentation.activity.MainActivity
import kotlinx.android.synthetic.main.item_my_ticket.view.*
import kotlinx.android.synthetic.main.item_passenger.view.*


class PassengerAdapter(val OnItemSelectedListener: (Place) -> Unit) :
    RecyclerView.Adapter<PassengerAdapter.MyViewHolder>() {

    private var dataList: List<Place> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_passenger, parent, false)
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Place>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(place: Place) {
            itemView.apply {
//                txtCityFromPassenger?.text = place.from?.city
//                txtStationFromPassenger?.text = place.from?.station
////                txtTimeFromPassenger?.text = place.departureTime?.getFormattedTime()
////                txtDateFromPassenger?.text = place.departureTime?.getFormattedDate()
//                txtCityToPassenger?.text = place.to?.city
//                txtStationToPassenger?.text = place.to?.station
////                txtTimeToPassenger?.text = place.destinationTime?.getFormattedTime()
////                txtDateToPassenger?.text = place.destinationTime?.getFormattedDate()
//                txtPricePassenger?.text = ""
//                txtBookingDatePassenger?.text =

                var places = ""
                for (item in place.number!!) {
                    var placeNumber = item
                    when (place.car?.carTypeId) {

                        AppConstants.CAR_TYPE_36 -> {

                            when {

                                placeNumber in 1..16 -> places += "${placeNumber}↓, "
                                placeNumber in 17..32 -> places += "${placeNumber.minus(16)}↑, "
                                placeNumber == 33 || placeNumber == 34 -> places += "0↑, "
                                placeNumber == 35 || placeNumber == 36 -> places += "0↓, "

                            }

                        } else ->{
                                places += "${placeNumber}, "
                            }

                        }
                    txtNumberPassenger?.text =
                        places.substring(0..(places.count().minus(3)))
                    txtNumberPassenger?.background = context.getDrawable(R.color.colorAccent)

                    }




                    txtStateNumberPassenger?.text = place.passenger?.phone
//                    txtPricePassenger?.text =
//                        context.getString(R.string.price_value, place.price.toString())
//                    //setOnClickListener { OnItemSelectedListener.invoke(travel) }
//                    txtBookingDatePassenger?.text = place.bookingTime.getFormattedDateAndTime()
                    txtStateNumberPassenger?.setOnClickListener {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse("tel: 8${place.passenger?.phone}")
                            )
                        )




                    }
                }
            }

        }

    }