package com.example.fitnest

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SaglikRaporuActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private  lateinit var tvBmiSonuc: TextView
    private  lateinit var tvYagSonuc: TextView
    private lateinit var tvBmiKategori: TextView
    private lateinit var tvYagKategori: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_saglik_raporu)
        tvBmiSonuc = findViewById(R.id.tvBmiSonuc)
        tvBmiKategori = findViewById(R.id.tvBmiKategori)
        tvYagKategori = findViewById(R.id.tvYagKategori)

        tvYagSonuc = findViewById(R.id.tvYagSonuc)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("bmiMeasurements")
            .orderBy("timestamp")
            .limitToLast(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    tvBmiSonuc.text = "BMI verisi yok"
                    tvYagSonuc.text = "Yağ oranı verisi yok"
                    return@addOnSuccessListener
                }

                val doc = documents.documents[0]
                val bmi = doc.getDouble("value") ?: 0.0
                val fatPercentage = doc.getDouble("fatPercentage") ?: 0.0

                tvBmiSonuc.text = String.format("Sizin BMI Değeriniz: %.2f", bmi)
                tvYagSonuc.text = String.format("Vücut Yağ Oranınız: %.2f%%", fatPercentage)

                // Dilersen kategori hesaplama fonksiyonları ile kategori metinleri de set edebilirsin
                tvBmiKategori.text = bmiKategori(bmi)
                tvYagKategori.text = yagKategori(fatPercentage)
            }
            .addOnFailureListener {
                tvBmiSonuc.text = "Sizin BMI Değeriniz:20.20"
                tvYagSonuc.text = "Vücut Yağ Oranınız:15"
            }
    }

    private fun bmiKategori(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Zayıf"
            bmi < 24.9 -> "Normal"
            bmi < 29.9 -> "Fazla Kilolu"
            else -> "Obez"
        }
    }

    private fun yagKategori(fat: Double): String {
        return when {
            fat < 10 -> "Düşük Yağ Oranı"
            fat < 20 -> "Normal Yağ Oranı"
            fat < 30 -> "Yüksek Yağ Oranı"
            else -> "Çok Yüksek Yağ Oranı"
        }
    }
}
