package com.example.kaloritakipuygulamasi

import com.example.kaloritakipuygulamasi.ItemModel
import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object MockList {
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    @SuppressLint("RestrictedApi")
    fun getMockedItemList(callback: (List<ItemModel>) -> Unit) {
        firestore.collection("yemekler")
            .get()
            .addOnSuccessListener { documents ->
                val itemList = ArrayList<ItemModel>()
                val pendingTasks = documents.size() // Bekleyen işlerin sayısı
                if (pendingTasks == 0) {
                    callback(emptyList())
                    return@addOnSuccessListener
                }

                for (document in documents) {
                    val ad = document.getString("ad") ?: "Ad bulunamadı"
                    val fotoField = document.get("foto")
                    val fotoUrl = when (fotoField) {
                        is String -> fotoField
                        is Map<*, *> -> fotoField["url"] as? String ?: "Foto bulunamadı"
                        else -> "Foto bulunamadı"
                    }
                    val kaloriField = document.get("kalori")
                    val kalori = when (kaloriField) {
                        is Number -> kaloriField.toInt()
                        is String -> kaloriField.toIntOrNull() ?: -1
                        else -> -1
                    }
                    itemList.add(ItemModel(ad, fotoUrl, kalori))
                    if (itemList.size == pendingTasks) {
                        callback(itemList)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MockList", "Error getting documents: ", exception)
                callback(emptyList())
            }
    }
}
