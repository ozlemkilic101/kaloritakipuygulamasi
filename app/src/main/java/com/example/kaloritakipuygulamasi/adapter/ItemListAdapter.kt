package com.example.kaloritakipuygulamasi.adapter

import ItemListViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.ItemModel
import com.example.kaloritakipuygulamasi.R

class ItemListAdapter(val itemList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<ItemListViewHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_image_with_text, // Aynı düzen kullan
            parent,
            false
        )
        return ItemListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(itemList[position])

        // Click event handling
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
