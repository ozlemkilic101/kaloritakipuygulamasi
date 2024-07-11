package com.example.kaloritakipuygulamasi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.R
import com.example.kaloritakipuygulamasi.adapter.ItemListAdapter
import com.example.kaloritakipuygulamasi.ItemModel
import com.example.kaloritakipuygulamasi.ItemModel2
import com.example.kaloritakipuygulamasi.adapter.ItemListAdapter2
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.Date

class Fragmentanasayfa : Fragment() {

    private lateinit var foodAdapter: ItemListAdapter
    private lateinit var exerciseAdapter: ItemListAdapter2
    private val foodItems: ArrayList<ItemModel> = arrayListOf()
    private val exerciseItems: ArrayList<ItemModel2> = arrayListOf()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var userEmail: String

    private var totalalınanCalories: Int = 0
    private var totalyakılanCalories: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        userEmail = auth.currentUser?.email ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragmentanasayfa2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewFood = view.findViewById<RecyclerView>(R.id.recyclerViewFoodIntake)
        val recyclerViewExercise = view.findViewById<RecyclerView>(R.id.recyclerViewBurnedCalories)

        foodAdapter = ItemListAdapter(foodItems)
        exerciseAdapter = ItemListAdapter2(exerciseItems)

        recyclerViewFood.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewFood.adapter = foodAdapter

        recyclerViewExercise.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewExercise.adapter = exerciseAdapter

        fetchFoodItems()
        fetchExerciseItems()
    }

    private fun fetchFoodItems() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        firestore.collection("kayit")
            .whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { result ->
                foodItems.clear()
                totalalınanCalories = 0 // Toplam kaloriyi sıfırla
                for (document in result) {
                    val ad = document.getString("yemekad") ?: "ad boş"
                    val foto = document.getString("foto") ?: "foto boş"
                    val kalori = document.getLong("kalori")?.toInt() ?: 0
                    val eklenmeZamani = document.getTimestamp("eklenmezamani")?.toDate()

                    if (eklenmeZamani != null && isSameDay(eklenmeZamani, today)) {
                        val foodItem = ItemModel(ad, foto, kalori)
                        foodItems.add(foodItem)
                        totalalınanCalories += kalori // Kaloriyi ekle
                    }
                }
                foodAdapter.notifyDataSetChanged()
                updateTotalCaloriesView() // Toplam kaloriyi güncelle
            }
            .addOnFailureListener { exception ->
                Log.e("Fragmentanasayfa", "Veri çekerken hata oluştu", exception)
            }
    }

    private fun fetchExerciseItems() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        firestore.collection("kayit2")
            .whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { result ->
                exerciseItems.clear()
                totalyakılanCalories = 0 // Toplam kaloriyi sıfırla

                for (document in result) {
                    val ad = document.getString("egzersizad") ?: "ad boş"
                    val foto = document.getString("foto") ?: "foto boş"
                    val kalori = document.getLong("kalori")?.toInt() ?: 0
                    val eklenmeZamani = document.getTimestamp("eklenmezamani")?.toDate()

                    if (eklenmeZamani != null && isSameDay(eklenmeZamani, today)) {
                        val exerciseItem = ItemModel2(ad, foto, kalori)
                        exerciseItems.add(exerciseItem)
                        totalyakılanCalories += kalori // Kaloriyi ekle
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
                updateTotalCaloriesView()
            }
            .addOnFailureListener { exception ->
                Log.e("Fragmentanasayfa", "Veri çekerken hata oluştu", exception)
            }
    }

    private fun updateTotalCaloriesView() {
        // Toplam kaloriyi gösteren bir TextView bulun ve güncelle
        val totalalınanCaloriesTextView = view?.findViewById<TextView>(R.id.txtalinan)
        totalalınanCaloriesTextView?.text = "Toplam Alınan Kalori: $totalalınanCalories"
        val totalyakılanCaloriesTextView=view?.findViewById<TextView>(R.id.txtyakilan)
        totalyakılanCaloriesTextView?.text="Toplam Yakılan Kalori: $totalyakılanCalories"

    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }
}
