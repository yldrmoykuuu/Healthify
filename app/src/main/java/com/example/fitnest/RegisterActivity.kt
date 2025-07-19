package com.example.fitnest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
val button2=findViewById<Button>(R.id.button2)
        val tvGiris=findViewById<TextView>(R.id.tvGiris)
        button2.setOnClickListener {
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
       tvGiris.setOnClickListener {
            var intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}