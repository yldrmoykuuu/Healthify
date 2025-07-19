package com.example.fitnest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val tvGirisMail = findViewById< EditText>(R.id.tvGirisMail)
        val tvGirisSifre = findViewById<EditText>(R.id.tvGirisSifre)
        val btnGiris = findViewById<Button>(R.id.btnGiris)


        btnGiris.setOnClickListener {
            if (tvGirisMail.text.isNotEmpty() && tvGirisSifre.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(tvGirisMail.text.toString(), tvGirisSifre.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, AnaMainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Giriş Başarısız", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this@LoginActivity, "Lütfen Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show()
            }
        }

       }}
