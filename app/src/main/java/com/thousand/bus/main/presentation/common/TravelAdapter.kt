package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.extension.getFormattedDateAndTime
import com.thousand.bus.global.extension.setImageUrl
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_travel.view.*

class TravelAdapter(val onBottomReachedListener: () -> Unit, val OnItemSelectedListener: (Travel) -> Unit, val OnBookingItemSelectedListener: (Travel) -> Unit, val OnImageItemSelectedListener:(Travel) -> Unit): RecyclerView.Adapter<TravelAdapter.MyViewHolder>(){

    private var dataList: List<Travel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_travel, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_LIMIT) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Travel>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(travel: Travel){
            itemView.apply {
                txtTransportNameTravel?.text = travel.car?.name

                if (travel.car?.carTypeId == AppConstants.CAR_TYPE_36)
                    txtBusType36Travel.visible(true)
                else
                    txtBusType36Travel.visible(false)


                txtDateTravel?.text = "${travel.departureTime.getFormattedDateAndTime()}\n${travel.destinationTime.getFormattedDateAndTime()}"
                txtLocationTravel?.text = "${travel.from?.city} - ${travel.from?.station}\n${travel.to?.city} - ${travel.to?.station}"
                if (travel.minPrice != null && travel.maxPrice != null)
                    txtPriceTravel?.text = context.getString(R.string.price_value, "${travel.minPrice}-${travel.maxPrice}")
                imgAvatarTravel?.setImageUrl(travel.car?.image)
                imgAvatarTravel?.setOnClickListener{OnImageItemSelectedListener.invoke(travel)}
                setOnClickListener { OnItemSelectedListener.invoke(travel) }
                btnBookTravel?.setOnClickListener { OnBookingItemSelectedListener.invoke(travel) }
                imgMapTravel?.visible((travel.from?.lat != null) || (travel.to?.lat != null))
            }
        }

    }

}