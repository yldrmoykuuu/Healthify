package com.example.fitnest

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class SporDetayActivity : AppCompatActivity() {

    private lateinit var spinnerSporTuru: Spinner
    private lateinit var etSure: EditText
    private lateinit var btnSporKaydet: Button
    private lateinit var tvTahminiKalori: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var btnVideo1: Button
    private lateinit var btnVideo2: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var chartSpor: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spor_detay)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        chartSpor = findViewById(R.id.chartSpor)
        spinnerSporTuru = findViewById(R.id.spinnerSporTuru)
        etSure = findViewById(R.id.etSure)
        btnSporKaydet = findViewById(R.id.btnSporKaydet)
        tvTahminiKalori = findViewById(R.id.tvTahminiKalori)
        btnVideo1 = findViewById(R.id.btnVideo1)
        btnVideo2 = findViewById(R.id.btnVideo2)

        fetchSporDataFromFireStore()

        val sporTurleri = listOf("Yürüyüş", "Koşu", "Bisiklet", "Yoga", "Kardiyo")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sporTurleri)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSporTuru.adapter = adapter

        btnSporKaydet.setOnClickListener {
            val secilenSpor = spinnerSporTuru.selectedItem.toString()
            val sureStr = etSure.text.toString()
            if (sureStr.isEmpty()) {
                Toast.makeText(this, "Lütfen süre girin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sure = sureStr.toInt()
            val kalori = sure * 5
            tvTahminiKalori.text = "Tahmini Kalori: $kalori"
            val now = System.currentTimeMillis()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = sdf.format(now)

            val userId = auth.currentUser?.uid
            if (userId != null) {
                val sporData = hashMapOf(
                    "tur" to secilenSpor,
                    "sure" to sure,
                    "kalori" to kalori,
                    "tarih" to dateString
                )

                firestore.collection("spor_kayitlari")
                    .document(userId)
                    .collection("kayitlar")
                    .add(sporData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Kayıt başarılı", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Kayıt başarısız", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Kullanıcı oturumu açık değil", Toast.LENGTH_SHORT).show()
            }
        }

        // Video butonlarına tıklama örneği:
        btnVideo1.setOnClickListener {
            openYoutubeVideo("https://www.youtube.com/watch?v=m970B6ZNL3s&t=408s")
            Toast.makeText(this, "Sabah Yogası videosu açılacak", Toast.LENGTH_SHORT).show()
        }

        btnVideo2.setOnClickListener {
            openYoutubeVideo("https://www.youtube.com/watch?v=n1FuHLcIJWw")
            Toast.makeText(this, "Evde Kardiyo videosu açılacak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchSporDataFromFireStore() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("spor_kayitlari")
            .document(userId)
            .collection("kayitlar")
            .get()
            .addOnSuccessListener { results ->
                val kaloriVerisi = mutableListOf<BarEntry>()
                val tarihVerisi = mutableListOf<String>()

                results.documents
                    .sortedBy { it.getString("tarih") }
                    .forEachIndexed { index, doc ->
                        val date = doc.getString("tarih") ?: "Tarih Yok"
                        val kalori = (doc.getDouble("kalori") ?: 0.0).toFloat()

                        kaloriVerisi.add(BarEntry(index.toFloat(), kalori))
                        tarihVerisi.add(date)
                    }

                setupBarChart(chartSpor, kaloriVerisi, "Yakılan Kalori", tarihVerisi)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Spor verileri alınamadı", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupBarChart(
        barChart: BarChart,
        entries: List<BarEntry>,
        label: String,
        tarihLabels: List<String>
    ) {
        val dataSet = BarDataSet(entries, label)
        dataSet.color = Color.parseColor("#3F51B5")
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        barChart.data = barData

        barChart.description.isEnabled = false
        barChart.setFitBars(true)

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(tarihLabels.map { it.take(5) })
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false

        barChart.invalidate()
    }

    private fun openYoutubeVideo(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage("com.google.android.youtube")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }
}
