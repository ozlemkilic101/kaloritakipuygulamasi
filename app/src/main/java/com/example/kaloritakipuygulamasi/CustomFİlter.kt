package com.example.kaloritakipuygulamasi

import android.util.Log
import android.view.View
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.ItemModel
import com.example.kaloritakipuygulamasi.adapter.ItemListAdapter

import java.util.ArrayList
import java.util.Locale

//this class is for filter.
class CustomFilter(
    private var filterList: ArrayList<ItemModel>,
    private var adapter: ItemListAdapter
) : Filter() {
    override fun performFiltering(charSequence: CharSequence?): FilterResults {
        val charString = charSequence?.toString()?.toLowerCase(Locale.ROOT) ?: ""
        val results = FilterResults()

        Log.d("Filter", "Filtering for: $charString")

        results.values = if (charString.isEmpty()) {
            filterList
        } else {
            filterList.filter {
                it.ad.toLowerCase(Locale.ROOT).contains(charString) ||
                        it.foto.toLowerCase(Locale.ROOT).contains(charString)
            } as ArrayList<ItemModel>
        }

        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        //    adapter.filteredNewsList = results.values as List<ItemModel>
        //  Log.d("Filter", "Filtered list: ${adapter.filteredNewsList.size} items")
        //adapter.notifyDataSetChanged()
        //}
    }


    class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ItemModel) {
            // item binding işlemleri burada yapılır
        }
    }
}