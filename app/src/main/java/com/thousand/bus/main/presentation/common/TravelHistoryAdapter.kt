package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.extension.*
import kotlinx.android.synthetic.main.item_history.view.*

class TravelHistoryAdapter(val OnItemSelectedListener: (Travel) -> Unit): RecyclerView.Adapter<TravelHistoryAdapter.MyViewHolder>(){

    private var dataList: List<Travel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))

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
                txtCityFromHistory?.text = travel.from?.city
                txtStationFromHistory?.text = travel.from?.station
                txtTimeFromHistory?.text = travel.departureTime?.getFormattedTime()
                txtDateFromHistory?.text = travel.departureTime?.getFormattedDate()
                txtCityToHistory?.text = travel.to?.city
                txtStationToHistory?.text = travel.to?.station
                txtTimeToHistory?.text = travel.destinationTime?.getFormattedTime()
                txtDateToHistory?.text = travel.destinationTime?.getFormattedDate()
                txtTransportTypeHistory?.text = travel.car?.type
                txtTransportNumberHistory?.text = travel.car?.stateNumber
                txtPriceHistory?.text = travel.price?.toString()
                txtDateHistory?.text = travel.createdAt?.getFormattedDateAndTime_()
                setOnClickListener { OnItemSelectedListener.invoke(travel) }
            }
        }

    }

}