package com.example.kaloritakipuygulamasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kaloritakipuygulamasi.FoodItem
import com.example.kaloritakipuygulamasi.R

class GridAdapter(private val context: Context, private val itemList: ArrayList<FoodItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = itemList[position]
        holder.bind(item)

        return view
    }

    private class ViewHolder(view: View) {
        private val itemName: TextView = view.findViewById(R.id.itemName)
        private val itemCalories: TextView = view.findViewById(R.id.itemCalories)


        fun bind(item: FoodItem) {
            itemName.text = item.name
            itemCalories.text = item.calories.toString()
        }
    }
}

