package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Car
import com.thousand.bus.global.extension.setImageUrl
import kotlinx.android.synthetic.main.item_car_list.view.*

class CarListAdapter(val OnItemSelectedListener: (Car) -> Unit) :
    RecyclerView.Adapter<CarListAdapter.MyViewHolder>() {

    private var dataList: List<Car> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_car_list, parent, false)
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Car>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(car: Car) {
            itemView.apply {
                if (car.carTypeId == 3 || car.carTypeId == 6) {
                    txtTypeTransport?.text = "Минивэн"
                } else if (car.carTypeId ==5){
                    txtTypeTransport?.text = "Машина"
                } else {
                    txtTypeTransport?.text = "Автобус"
                }
                txtNumberTransport?.text = car.stateNumber
                imgAvatarCarList?.setImageUrl(car.avatar)
                setOnClickListener { OnItemSelectedListener.invoke(car) }

                if (car.is_confirmed == 0){
                    cardViewCarList.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorRed3))
                    txtWaitTransport?.text ="В ожидании"
                }
            }
        }

    }

}