package com.example.kaloritakipuygulamasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kaloritakipuygulamasi.databinding.ActivityAnasayfaBinding
import com.example.kaloritakipuygulamasi.fragment.Fragmentanasayfa
import com.example.kaloritakipuygulamasi.fragment.egzersizeklefragment
import com.example.kaloritakipuygulamasi.fragment.kullanicifragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class anasayfaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnasayfaBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anasayfa)
        loadFragment(Fragmentanasayfa())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itanasayfa -> {
                    loadFragment(Fragmentanasayfa())
                    true
                }
                R.id.ityemekekle -> {
                    loadFragment(DenemeFragment())
                    true
                }
                R.id.itegzersizekle -> {
                    loadFragment(egzersizeklefragment())
                    true
                }
                R.id.itkullanici -> {
                    loadFragment(kullanicifragment())
                    true
                }
                else -> false
            }
        }

        // Firebase Database referansını al
        database = FirebaseDatabase.getInstance().reference

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment) // fragment_container, fragment container'ının gerçek ID'si olarak değiştirilmeli
        transaction.commit()
    }


}
