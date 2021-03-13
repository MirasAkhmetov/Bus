package com.thousand.bus.main.presentation.driver.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.FromTo
import com.thousand.bus.global.extension.getFormattedDate
import com.thousand.bus.global.extension.getFormattedDateAndTime
import kotlinx.android.synthetic.main.item_date_from_to.view.*

class DateFromToAdapter(
    val onFromItemSelectedListener: (FromTo) -> Unit,
    val onToItemSelectedListener: (FromTo) -> Unit,
    val onDeleteItemListener: (Int) -> Unit
): RecyclerView.Adapter<DateFromToAdapter.MyViewHolder>(){

    private var dataList: MutableList<FromTo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_date_from_to, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun submitData(dataList: MutableList<FromTo>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(fromTo: FromTo, position: Int){
            itemView.apply {
                txtDateFromItem?.text = fromTo.departureTime.getFormattedDateAndTime()
                txtDateToItem?.text = fromTo.destinationTime.getFormattedDateAndTime()
                txtDateFromItem?.setOnClickListener { onFromItemSelectedListener.invoke(fromTo) }
                txtDateToItem?.setOnClickListener { onToItemSelectedListener.invoke(fromTo) }
                btnDeleteDateFromTo?.setOnClickListener { onDeleteItemListener.invoke(position) }
            }
        }

    }

}