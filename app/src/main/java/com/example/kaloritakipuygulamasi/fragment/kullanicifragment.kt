package com.example.kaloritakipuygulamasi.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kaloritakipuygulamasi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import kotlin.math.pow

class kullanicifragment : Fragment() {

    private lateinit var textViewKullaniciAdi: TextView
    private lateinit var textViewSoyadi: TextView
    private lateinit var textViewBoy:TextView
    private lateinit var textViewKilo:TextView
    private lateinit var textViewBKI:TextView
    private lateinit var vt: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vt = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragmentkullanici2, container, false)

        // Initialize your views here
        textViewKullaniciAdi = view.findViewById(R.id.textviewKullaniciAdi)
        textViewSoyadi = view.findViewById(R.id.textviewSoyadi)
        textViewBoy=view.findViewById(R.id.textviewBoy)
        textViewKilo=view.findViewById(R.id.textviewKilo)
        textViewBKI=view.findViewById(R.id.textviewBKI)


        // Get the current user
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            Log.d(TAG, "Current user UID: $uid") // Log the UID to ensure it's correct

            // Fetch data from Firestore for the current user and set to TextViews
            vt.collection("user")
                .whereEqualTo("uid", uid) // UID'yi sorgulamak için whereEqualTo kullanın
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            Log.d(TAG, "${document.id} => ${document.data}")
                            textViewKullaniciAdi.text = document.getString("ad") ?: "Adı bulunamadı"
                            textViewSoyadi.text = document.getString("soyad") ?: "Soyadı bulunamadı"
                            val boy = document.getLong("boy")  ?: 0
                            val kilo = document.getDouble("kilo") ?: 0.0
                            textViewBoy.text=boy.toString()
                            textViewKilo.text=kilo.toString()
                            val boyMetre = boy.toDouble() / 100 // Boyu santimetreden metreye çevirin
                            val bki = kilo / (boyMetre.pow(2)) // BKI hesapla
                            val bkiFormatted = String.format("%.2f", bki) // BKI'yi virgülden sonra iki hane olacak şekilde biçimlendirin
                            textViewBKI.text = bkiFormatted // Dizeye çevirip TextView'e yazın
                        }
                    } else {
                        Log.d(TAG, "No such document")
                        textViewKullaniciAdi.text = "Adı bulunamadı"
                        textViewSoyadi.text = "Soyadı bulunamadı"

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting document.", exception)
                    textViewKullaniciAdi.text = "Adı bulunamadı"
                    textViewSoyadi.text = "Soyadı bulunamadı"
                }
        } else {
            Log.d(TAG, "No user is signed in")
            textViewKullaniciAdi.text = "Kullanıcı bulunamadı"
            textViewSoyadi.text = "Kullanıcı bulunamadı"
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            kullanicifragment().apply {
                arguments = Bundle().apply {
                    // Add your parameters here
                }
            }
    }
}
