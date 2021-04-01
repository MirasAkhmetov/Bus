package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.BusSeat
import kotlinx.android.synthetic.main.item_travel_search_bus_seat.view.*

class TravelBusSeatAdapter(private var dataList: List<BusSeat>) :
    RecyclerView.Adapter<TravelBusSeatAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TravelBusSeatAdapter.MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_travel_search_bus_seat,
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: TravelBusSeatAdapter.MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bind(busSeat: BusSeat) {
            itemView.apply {
                when (busSeat.state) {
                    BusSeat.STATE_FREE -> imgBusSeat2?.setImageResource(R.drawable.ic_bus_seat_travel)
                    BusSeat.STATE_NOT_FREE -> imgBusSeat2?.setImageResource(R.drawable.img_4)
                }
            }
        }

    }
}