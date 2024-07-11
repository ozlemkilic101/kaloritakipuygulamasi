package com.example.kaloritakipuygulamasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kaloritakipuygulamasi.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        binding.btnuyeol.setOnClickListener {
            val intent = Intent(this, uyeolma::class.java)
            startActivity(intent)
        }

        binding.btngirisyap.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.sifre.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                val intent = Intent(this, anasayfaActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.w("loginActivity", "Giriş Başarısız: Kullanıcı null")
                                Toast.makeText(baseContext, "Giriş Başarısız.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.w("loginActivity", "Giriş Başarısız: ${task.exception?.message}")
                            Toast.makeText(baseContext, "Giriş Başarısız: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Lütfen email ve şifreyi girin.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
