package com.thousand.bus.main.presentation.driver.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.PriceItem
import kotlinx.android.synthetic.main.item_price.view.*
import timber.log.Timber

class PriceAdapter(val onDeleteItemListener: (Int) -> Unit): RecyclerView.Adapter<PriceAdapter.MyViewHolder>(){

    private var dataList: MutableList<PriceItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_price, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun submitData(dataList: MutableList<PriceItem>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val incDecPrice = 500

        @SuppressLint("SetTextI18n")
        fun bind(priceItem: PriceItem, position: Int){
            itemView.apply {
                txtPlacesPriceItem?.text = context.getString(R.string.order_from_to_place, priceItem.from ?: 0, priceItem.to ?: 0)
                txtPriceItem?.text = context.getString(R.string.price_value, priceItem.price.toString())

                imgPlusPriceItem?.setOnClickListener {
                    priceItem.price = (priceItem.price ?: 0) + incDecPrice
                    notifyDataSetChanged()
                }
                imgMinusPriceItem?.setOnClickListener {
                    if ((priceItem.price ?: 0 - incDecPrice) > incDecPrice) {
                        priceItem.price = (priceItem.price ?: 0) - incDecPrice
                        notifyDataSetChanged()
                    }
                }
                txtPlacesPriceItem?.setOnClickListener { onDeleteItemListener.invoke(position) }
            }
        }

    }

}