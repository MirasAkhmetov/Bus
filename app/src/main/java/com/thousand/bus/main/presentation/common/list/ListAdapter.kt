package com.thousand.bus.main.presentation.common.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bus.R
import com.thousand.bus.entity.ListItem
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(val onItemSelectedListener: (ListItem) -> Unit): RecyclerView.Adapter<ListAdapter.PhotoHolder>(){

    private var dataList: List<ListItem> = listOf()
    private var isMultiple: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(isMultiple: Boolean, dataList: List<ListItem>){
        this.dataList = dataList
        this.isMultiple = isMultiple
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(listItem: ListItem){
            itemView.apply {
                txtTitleListItem.text = listItem.title
                if (listItem.selected) imgReadyListItem.visibility = View.VISIBLE else imgReadyListItem.visibility = View.INVISIBLE

                setOnClickListener {
                    if (!isMultiple){
                        listItem.selected = true
                        onItemSelectedListener.invoke(listItem)
                    }else{
                        listItem.selected = !listItem.selected
                        notifyDataSetChanged()
                    }
                }
            }
        }

    }

}