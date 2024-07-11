package com.example.kaloritakipuygulamasi.adapter

import ItemListViewHolder2
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.ItemModel2
import com.example.kaloritakipuygulamasi.R

class ItemListAdapter2(val itemList2: ArrayList<ItemModel2>) :
    RecyclerView.Adapter<ItemListViewHolder2>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_image_with_text, // Aynı düzen kullan
            parent,
            false
        )
        return ItemListViewHolder2(view)
    }

    override fun getItemCount(): Int {
        return itemList2.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder2, position: Int) {
        holder.bindItems(itemList2[position])

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
