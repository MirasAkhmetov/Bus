package com.thousand.bus.main.presentation.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.*
import com.thousand.bus.global.extension.setImageUrl
import com.thousand.bus.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_feedback.view.*


class FeedbackAdapter(val onBottomReachedListener: () -> Unit) : RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>(){

    private var dataList: List<Review> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_feedback, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == dataList.size - 5) onBottomReachedListener.invoke()

        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Review>){
        this.dataList = dataList
        notifyDataSetChanged()
    }


    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(review: Review){
            itemView.apply {

                avatarFeedback?.setImageUrl(review.avatar)
                txtNameFeedback?.text = "${review.name} ${review.surname}"
                ratingBarFeedback?.rating = review.rating!!.toFloat()
                txtComment?.text = review.text
            }

        }
    }
}