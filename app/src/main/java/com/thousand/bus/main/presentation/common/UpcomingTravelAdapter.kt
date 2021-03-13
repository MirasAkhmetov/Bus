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
import kotlinx.android.synthetic.main.item_upcoming_travel.view.*

class UpcomingTravelAdapter(val OnItemSelectedListener: (Travel) -> Unit, val onDeleteItemListener: (Travel) -> Unit): RecyclerView.Adapter<UpcomingTravelAdapter.MyViewHolder>(){

    private var dataList: List<Travel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming_travel, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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
                txtTransportNameUpcomingTravel?.text = travel.car?.name
                txtCityFromUpcomingTravel?.text = travel.from?.city
                txtStationFromUpcomingTravel?.text = travel.from?.station
                txtCityToUpcomingTravel?.text = travel.to?.city
                txtStationToUpcomingTravel?.text = travel.to?.station
                txtDateAndTimeUpcomingTravel?.text = travel.departureTime.getFormattedDateAndTime()
                if (travel.minPrice != null && travel.maxPrice != null)
                    txtPriceUpcomingTravel?.text = context.getString(R.string.price_value, "${travel.minPrice}-${travel.maxPrice}")
                imgAvatarUpcomingTravel?.setImageUrl(travel.car?.image)
                setOnClickListener { OnItemSelectedListener.invoke(travel) }
                btnDeleteUpcomingTravel?.setOnClickListener { onDeleteItemListener.invoke(travel) }
            }
        }

    }

}