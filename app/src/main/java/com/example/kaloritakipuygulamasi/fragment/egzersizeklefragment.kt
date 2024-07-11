package com.example.kaloritakipuygulamasi.fragment

import com.example.kaloritakipuygulamasi.ItemModel2
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.FragmentDetay
import com.example.kaloritakipuygulamasi.FragmentDetay2
import com.example.kaloritakipuygulamasi.R
import com.example.kaloritakipuygulamasi.adapter.ItemListAdapter2
import com.example.kaloritakipuygulamasi.MockList2

class egzersizeklefragment : Fragment() {

    private lateinit var adapter: ItemListAdapter2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragmentegzersizekle2, container, false)
        val recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerview2)
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())

        MockList2.getMockedItemList { items2 ->
            adapter = ItemListAdapter2(items2 as ArrayList<ItemModel2>)
            recyclerView2.adapter = adapter

            adapter.setOnItemClickListener(object : ItemListAdapter2.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val selectedItem = items2[position]

                    // FragmentDetay'a geçiş yap ve tıklanan öğenin verilerini aktar
                    val fragmentDetay = FragmentDetay2()
                    val bundle = Bundle()
                    bundle.putString("ad", selectedItem.ad)
                    bundle.putString("foto", selectedItem.foto)
                    bundle.putInt("kalori", selectedItem.kalori)
                    fragmentDetay.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentDetay)
                        .addToBackStack(null)
                        .commit()
                }
            })
        }

        return view
    }
}
