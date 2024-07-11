package com.example.kaloritakipuygulamasi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kaloritakipuygulamasi.databinding.ActivityUyeolBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    return emailRegex.matches(email)
}

class uye {
    var uno: Int = 0
    var ad: String = ""
    var soyad: String = ""
    var yas: Int = 0
    var cinsiyet: String = ""
    var kilo: Int = 0
    var boy: Int = 0
    var sifre: String = ""
    var mail: String = ""

    constructor(ad: String, soyad: String, yas: Int, cinsiyet: String, kilo: Int, boy: Int, sifre: String, mail: String) {
        this.ad = ad
        this.soyad = soyad
        this.yas = yas
        this.cinsiyet = cinsiyet
        this.kilo = kilo
        this.boy = boy
        this.sifre = sifre
        this.mail = mail
    }
}

class uyeolma : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in
        }
    }

    private lateinit var binding: ActivityUyeolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding = ActivityUyeolBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        uyeOl()
    }

    fun uyeOl() {
        val db = Firebase.firestore
        binding.btnolustur.setOnClickListener {

            val etad = binding.txtad.text.toString()
            val etsoyad = binding.txtsoyad.text.toString()
            val etyas = binding.txtyas.text.toString().toInt()
            val etcinsiyet = binding.txtcinsiyet.text.toString()
            val etkilo = binding.txtkilo.text.toString().toInt()
            val etboy = binding.txtboy.text.toString().toInt()
            val etsifre = binding.txtsifre.text.toString()
            val etmail = binding.txtemail.text.toString()

            val isValid = isValidEmail(etmail)

            if (isValid) {
                auth.createUserWithEmailAndPassword(etmail, etsifre)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                val userDocument = hashMapOf(
                                    "uid" to user.uid,
                                    "ad" to etad,
                                    "soyad" to etsoyad,
                                    "yas" to etyas,
                                    "cinsiyet" to etcinsiyet,
                                    "kilo" to etkilo,
                                    "boy" to etboy,
                                    "sifre" to etsifre,
                                    "mail" to etmail
                                )
                                db.collection("user")
                                    .add(userDocument)  // add() metodu kullanılarak ekleme işlemi yapılır
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                        Toast.makeText(this, "eklendi", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                        Toast.makeText(this, "eklenemedii", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Lütfen geçerli bir e-posta adresi girin.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
