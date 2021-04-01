package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.BusSeat
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.extension.getFormattedTime
import com.thousand.bus.global.extension.setImageUrl
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_travel.view.*

class TravelAdapter(
    val onBottomReachedListener: () -> Unit,
    val OnBookingItemSelectedListener: (Travel) -> Unit,
    val OnImageItemSelectedListener: (Travel) -> Unit
) : RecyclerView.Adapter<TravelAdapter.MyViewHolder>() {

    private var dataList: List<Travel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_travel,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_LIMIT) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Travel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(travel: Travel) {
            itemView.apply {

                //    if (travel.car?.carTypeId == AppConstants.CAR_TYPE_36)
                //txtBusType36Travel.visible(true)
                //   else
                //     txtBusType36Travel.visible(false)


                txtDateTravel?.text = travel.departureTime.getFormattedTime()
                txtFromCityTravel?.text = travel.from?.city
                txtToCityTravel?.text = travel.to?.city
                txtFromStationTravel?.text = travel.from?.station
                txtToStationTravel?.text = travel.to?.station
                if (travel.car?.rating != null) {
                    ratingBarTravel?.rating = travel.car.rating!!.toFloat()
                    txtRatingTravel?.text =
                        context.getString(R.string.number_feedback, travel.car?.rating)
                } else {
                    ratingBarTravel?.rating = 5F
                    txtRatingTravel?.text = "5,0"
                }

                if (travel.minPrice != null && travel.maxPrice != null) {
                    txtPriceTravel?.text = travel.minPrice.toString()
                    txtMaxPriceTravel?.text = travel.maxPrice.toString()
                }
                imgAvatarTravel?.setImageUrl(travel.car?.avatar)
                imgAvatarTravel?.setOnClickListener { OnImageItemSelectedListener.invoke(travel) }
                setOnClickListener { OnBookingItemSelectedListener.invoke(travel) }


                when (travel.car?.carTypeId) {
                    AppConstants.CAR_TYPE_50, AppConstants.CAR_TYPE_36, AppConstants.CAR_TYPE_62 -> {
                        recyclerTravelSeat.visible(false)
                        txtCountFreePlace.visible(true)
                        txtCountFreePlace?.text = travel.countFreePlaces.toString()
                        txtPlaceTravel?.text = context.getString(R.string.order_free_places)
                    }
                    else -> {
                        recyclerTravelSeat.visible(true)
                        txtCountFreePlace.visible(false)
                        txtPlaceTravel?.text = context.getString(R.string.order_id_place)

                        recyclerTravelSeat.apply {
                            this.adapter = when (travel.car?.carTypeId) {
                                AppConstants.CAR_TYPE_TAXI -> {
                                    travel.countFreePlaces?.let {
                                        BusSeat.getTaxiSeatTravel.subList(
                                            0,
                                            it
                                        )
                                    }?.let {
                                        TravelBusSeatAdapter(
                                            it
                                        )
                                    }
                                }
                                AppConstants.CAR_TYPE_ALPHARD -> travel.countFreePlaces?.let {
                                    BusSeat.getAlphardSeat.subList(
                                        0,
                                        it
                                    )
                                }?.let { TravelBusSeatAdapter(it) }
                                else -> travel.countFreePlaces?.let {
                                    BusSeat.getMinivanSeat.subList(
                                        0,
                                        it
                                    )
                                }?.let { TravelBusSeatAdapter(it) }

                            }
                            setRecycledViewPool(RecyclerView.RecycledViewPool())
                        }
                    }
                }


            }
        }

    }

}