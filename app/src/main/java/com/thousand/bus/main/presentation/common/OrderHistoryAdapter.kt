package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.OrderHistory
import com.thousand.bus.entity.Travel
import com.thousand.bus.global.extension.getFormattedDate
import com.thousand.bus.global.extension.getFormattedDateAndTime
import com.thousand.bus.global.extension.getFormattedTime
import kotlinx.android.synthetic.main.item_history.view.*

class OrderHistoryAdapter(val OnItemSelectedListener: (OrderHistory) -> Unit): RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder>(){

    private var dataList: List<OrderHistory> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<OrderHistory>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(orderHistory: OrderHistory){
            itemView.apply {
                txtCityFromHistory?.text = orderHistory.fromCity
                txtStationFromHistory?.text = orderHistory.fromStation
                txtTimeFromHistory?.text = orderHistory.departureTime?.getFormattedTime()
                txtDateFromHistory?.text = orderHistory.departureTime?.getFormattedDate()
                txtCityToHistory?.text = orderHistory.toCity
                txtStationToHistory?.text = orderHistory.toStation
                txtTransportTypeHistory?.text = orderHistory.carType
                txtPriceHistory?.text = orderHistory.price.toString()
                txtDateHistory?.text = orderHistory.createdAt?.getFormattedDateAndTime()
                txtTransportNumberHistory?.text = orderHistory.carStateNumber
                setOnClickListener { OnItemSelectedListener.invoke(orderHistory) }
            }
        }

    }

}