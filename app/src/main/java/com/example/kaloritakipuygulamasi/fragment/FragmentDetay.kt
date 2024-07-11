package com.example.kaloritakipuygulamasi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentDetay : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detay, container, false)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // TextView'ları ve ImageView'ı bul
        val textViewAd = view.findViewById<TextView>(R.id.txtegzersizadi)
        val imgviewFoto = view.findViewById<ImageView>(R.id.imageView)
        val textviewkalori = view.findViewById<TextView>(R.id.txtyakilankalori)
        val buttonekle = view.findViewById<Button>(R.id.buttonEkle)
        val editTextMiktar = view.findViewById<EditText>(R.id.editTextMiktar)


        // Bundle'dan verileri al
        val ad = arguments?.getString("ad")
        val foto = arguments?.getString("foto")
        val kalori = arguments?.getInt("kalori")

        // Verileri TextView ve ImageView'a yerleştir
        textViewAd.text = ad
        if (!foto.isNullOrEmpty()) {
            Glide.with(this).load(foto).into(imgviewFoto)
        }
        textviewkalori.text = kalori.toString() + " kcal / 100 gr"

        // Butona tıklandığında Firestore'a veri ekle
        buttonekle.setOnClickListener {
            val miktarStr = editTextMiktar.text.toString()
            val miktar = miktarStr.toIntOrNull() ?: 0
            val userEmail = auth.currentUser?.email ?: ""
            val eklenmezamani = com.google.firebase.Timestamp.now()
            if (!ad.isNullOrEmpty() && kalori != null && userEmail.isNotEmpty()) {
                // Firestore'a yeni bir belge ekleyelim
                val yeniYemek = hashMapOf(
                    "yemekad" to ad,
                    "foto" to foto,
                    "kalori" to kalori,
                    "miktar" to miktar,
                    "email" to userEmail,
                    "eklenmezamani" to eklenmezamani
                )

                // Firestore koleksiyonu ve belge referansı oluşturma
                val collectionRef = firestore.collection("kayit")
                collectionRef.add(yeniYemek)
                    .addOnSuccessListener { documentReference ->
                        Log.d("FragmentDetay", "Yeni yemek başarıyla eklendi. Belge ID: ${documentReference.id}")
                        parentFragmentManager.popBackStack()
                    }
                    .addOnFailureListener { e ->
                        Log.e("FragmentDetay", "Yeni yemek eklenirken hata oluştu", e)
                    }
            } else {
                Log.e("FragmentDetay", "Eksik veya geçersiz veri. Yemek adı: $ad, Kalori: $kalori, Kullanıcı email: $userEmail")
            }
        }

        return view
    }
}
