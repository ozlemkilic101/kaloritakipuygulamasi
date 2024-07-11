package com.example.kaloritakipuygulamasi

import com.example.kaloritakipuygulamasi.ItemModel
import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object MockList2 {
    private val firestore2: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    @SuppressLint("RestrictedApi")
    fun getMockedItemList(callback: (List<ItemModel2>) -> Unit) {
        firestore2.collection("egzersiz")
            .get()
            .addOnSuccessListener { documents ->
                val itemList2 = ArrayList<ItemModel2>()
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
                    itemList2.add(ItemModel2(ad, fotoUrl, kalori))

                    if (itemList2.size == pendingTasks) {
                        callback(itemList2)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MockList2", "Error getting documents: ", exception)
                callback(emptyList())
            }
    }
}




