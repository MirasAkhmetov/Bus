package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.Ticket
import com.thousand.bus.global.extension.getFormattedDate
import com.thousand.bus.global.extension.getFormattedTime
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_my_ticket.view.*
import java.text.SimpleDateFormat
import java.util.*

class TicketAdapter(val onTakeBackBtnClicked:(Ticket) -> Unit): RecyclerView.Adapter<TicketAdapter.MyViewHolder>(){

    private var dataList: List<Ticket> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_ticket, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Ticket>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(ticket: Ticket){
            itemView.apply {
                txtTimeFromMyTicket?.text = ticket.departureTime?.getFormattedTime()
                txtTimeToMyTicket?.text = ticket.destinationTime?.getFormattedTime()
                txtDateFromMyTicket?.text = ticket.departureTime?.getFormattedDate()
                txtDateToMyTicket?.text = ticket.destinationTime?.getFormattedDate()
                txtCityFromMyTicket?.text = ticket.fromCity
                txtStationFromMyTicket?.text = ticket.fromStation
                txtCityToMyTicket?.text = ticket.toCity
                txtStationToMyTicket?.text = ticket.toStation
                txtCarNumberMyTicket?.text = ticket.carStateNumber

                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val departureDate = simpleDateFormat.parse(ticket.departureTime)
                val calendarInstance = Calendar.getInstance()
                calendarInstance.time.time = calendarInstance.time.time + (60 * 60 * 1000)

                if(departureDate?.after(calendarInstance.time) ?: false)
                    imgMainMyTicket.setColorFilter(null)
                else
                    imgMainMyTicket.setColorFilter(ContextCompat.getColor(context, R.color.colorRed))

                if(ticket.status =="in_process"){
                    txtInProgress?.text = "В ожидании"
                    btnCancelMyTicket?.visible(false)
                    imgMainMyTicket?.setColorFilter(ContextCompat.getColor(context, R.color.colorYellow))
                }
                else {
                    imgMainMyTicket?.setColorFilter(null)
                    btnCancelMyTicket.visible(departureDate?.after(calendarInstance.time))
                }

                if (ticket.car_type_count_places == 36) {

                    ticket.number?.let {
                        if (it in 17..32) {
                            txtPlaceNumberMyTicket?.text = "${it.minus(16)}↑"
                        } else {

                            if (it == 33 || it == 34) {
                                txtPlaceNumberMyTicket?.text = "0↑"
                            } else if (it == 35 || it == 36) {
                                txtPlaceNumberMyTicket?.text = "0↓"
                            } else {
                                txtPlaceNumberMyTicket?.text = "$it↓"
                            }
                        }
                    }

                } else {
                    txtPlaceNumberMyTicket?.text = ticket.number.toString()
                }

                btnCancelMyTicket?.setOnClickListener {
                    val dialog =  AlertDialog.Builder(context)
                        .setMessage(context.getString(R.string.are_you_sure_return_ticket))
                        .setPositiveButton(
                            context.getString(R.string.yes)
                        ) { dialog, _ ->
                            onTakeBackBtnClicked.invoke(ticket)
                            dialog.dismiss()
                        }
                        .setNegativeButton(
                            context.getString(R.string.no)
                        ){ dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                    dialog.show()
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                }

            }
        }

    }

}