package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.BusSeat
import com.thousand.bus.entity.BusSeat.Companion.STATE_CANCEL
import com.thousand.bus.entity.BusSeat.Companion.STATE_DRIVER
import com.thousand.bus.entity.BusSeat.Companion.STATE_EMPTY
import com.thousand.bus.entity.BusSeat.Companion.STATE_FREE
import com.thousand.bus.entity.BusSeat.Companion.STATE_IN_PROCESS
import com.thousand.bus.entity.BusSeat.Companion.STATE_NOT_FREE
import com.thousand.bus.entity.BusSeat.Companion.STATE_OUT
import com.thousand.bus.entity.BusSeat.Companion.STATE_PASS_BOTTOM_LEFT
import com.thousand.bus.entity.BusSeat.Companion.STATE_PASS_BOTTOM_RIGHT
import com.thousand.bus.entity.BusSeat.Companion.STATE_PASS_CENTER
import com.thousand.bus.entity.BusSeat.Companion.STATE_PASS_TOP_LEFT
import com.thousand.bus.entity.BusSeat.Companion.STATE_PASS_TOP_RIGHT
import com.thousand.bus.entity.BusSeat.Companion.STATE_YOUR
import com.thousand.bus.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_bus_seat.view.*


class BusSeatAdapter(val OnItemSelectedListener: (BusSeat) -> Unit): RecyclerView.Adapter<BusSeatAdapter.MyViewHolder>(){

    private var dataList: List<BusSeat> = listOf()
    private var typeBus: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bus_seat, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<BusSeat>, typeBus: Int) {
        this.dataList = dataList
        this.typeBus = typeBus
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("ResourceType")
        fun bind(busSeat: BusSeat){
            itemView.apply {

                when (typeBus) {
                    AppConstants.CAR_TYPE_50 -> {
                        txtBusSeat?.text = if (busSeat.id != null) busSeat.id.toString() else ""
                    }
                    AppConstants.CAR_TYPE_ALPHARD -> {
                        txtBusSeat?.text = if (busSeat.id != null) busSeat.id.toString() else ""
                    }
                    AppConstants.CAR_TYPE_TAXI -> {
                        txtBusSeat?.text = if (busSeat.id != null) busSeat.id.toString() else ""
                    }
                    AppConstants.CAR_TYPE_MINIVAN -> {
                        txtBusSeat?.text = if (busSeat.id != null) busSeat.id.toString() else ""
                    }
                    AppConstants.CAR_TYPE_36 -> {

                        txtBusSeat.setPadding(3, 0, 0, 0)
                        txtBusSeat.textSize = context.resources.getDimension(R.dimen.txt_place)

                        if (busSeat.typeUpDown == 1) {
                            txtBusSeat?.text = if (busSeat.upDownId != null) "${busSeat.upDownId}↓" else ""
                        } else {
                            txtBusSeat?.text = if (busSeat.upDownId != null) "${busSeat.upDownId}↑" else ""
                        }
                    }
                }

                imgBusSeat?.setBackgroundResource(R.drawable.img_15)
                imgBusSeat?.setImageResource(R.drawable.img_15)
                imgBusSeat?.colorFilter = null

                when(busSeat.state) {
                    STATE_FREE -> imgBusSeat?.setImageResource(R.drawable.img_5)
                    STATE_NOT_FREE -> imgBusSeat?.setImageResource(R.drawable.img_4)
                    STATE_IN_PROCESS -> imgBusSeat?.setImageResource(R.drawable.img_6)
                    STATE_YOUR -> imgBusSeat?.setImageResource(R.drawable.img_14)
                    STATE_CANCEL -> {
                        imgBusSeat?.setImageResource(R.drawable.img_4)
                        imgBusSeat?.setColorFilter(ContextCompat.getColor(context, R.color.colorRed))
                    }
                    STATE_DRIVER -> imgBusSeat?.setImageResource(R.drawable.img_7)
                    STATE_EMPTY -> imgBusSeat?.setBackgroundResource(R.drawable.img_15)
                    STATE_OUT -> imgBusSeat?.setImageResource(R.drawable.img_8)
                    STATE_PASS_TOP_LEFT -> imgBusSeat?.setBackgroundResource(R.drawable.img_9)
                    STATE_PASS_TOP_RIGHT -> imgBusSeat?.setBackgroundResource(R.drawable.img_10)
                    STATE_PASS_CENTER -> imgBusSeat?.setBackgroundResource(R.drawable.img_13)
                    STATE_PASS_BOTTOM_LEFT -> imgBusSeat?.setBackgroundResource(R.drawable.img_12)
                    STATE_PASS_BOTTOM_RIGHT -> imgBusSeat?.setBackgroundResource(R.drawable.img_11)
                }

                setOnClickListener {
                    OnItemSelectedListener.invoke(busSeat)
                }
            }
        }

    }

}