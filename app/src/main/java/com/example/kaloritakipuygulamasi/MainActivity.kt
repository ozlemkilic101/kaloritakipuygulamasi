package com.example.kaloritakipuygulamasi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaloritakipuygulamasi.adapter.myadapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.kaloritakipuygulamasi.databinding.ActivityLoginBinding // Import edilmesi gereken satır

class MainActivity : AppCompatActivity() {

    lateinit var binding2: ActivityLoginBinding

    private val db = FirebaseFirestore.getInstance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var yemekliste: ArrayList<FoodItem>
    private lateinit var myadapter: myadapter
    private lateinit var vt: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        yemekliste = arrayListOf()

        myadapter = myadapter(yemekliste)

        recyclerView.adapter = myadapter
        EventChangeListener()
    }

    private fun EventChangeListener() {
        vt = FirebaseFirestore.getInstance()
        vt.collection("besinkalori").orderBy("ad", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return@addSnapshotListener
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        yemekliste.add(dc.document.toObject(FoodItem::class.java))
                    }
                }
                myadapter.notifyDataSetChanged()
            }
    }
}
