package com.thousand.bus.main.presentation.customer.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Station
import com.thousand.bus.entity.TravelStation
import kotlinx.android.synthetic.main.item_station.view.*

class StationAdapter : RecyclerView.Adapter<StationAdapter.MyViewHolder>(){

    private var dataList: List<Station> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Station>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(travelStation: Station){
            itemView.apply {
                txtNameStation?.text = travelStation.stations
            }
        }
    }

}