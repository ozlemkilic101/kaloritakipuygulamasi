package com.example.kaloritakipuygulamasi.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.FoodItem
import com.example.kaloritakipuygulamasi.R

class myadapter(private val yemekliste: ArrayList<FoodItem>):RecyclerView.Adapter<myadapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myadapter.MyViewHolder {
      val itemView= LayoutInflater.from(parent.context).inflate(R.layout.listitem,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val yemek: FoodItem = yemekliste[position]
        holder.yemekad.text = yemek.name
        holder.kalori.text = yemek.calories.toString() // Int değeri String'e dönüştürüldü
    }


    override fun getItemCount(): Int {

       return yemekliste.size

    }

    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val yemekad: TextView =itemView.findViewById(R.id.txtyemekad)
        val kalori: TextView =itemView.findViewById(R.id.txtkalori)

    }

}
