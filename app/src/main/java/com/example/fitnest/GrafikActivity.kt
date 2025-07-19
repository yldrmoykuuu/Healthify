package com.example.fitnest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GrafikActivity : AppCompatActivity() {
    private lateinit var spinnerGraphSelector: Spinner

    private lateinit var chartKilo: BarChart
    private lateinit var chartBoy: BarChart
    private lateinit var chartBel: BarChart
    private lateinit var chartKalca: BarChart
    private lateinit var chartGogus: BarChart

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private val charts by lazy {
        mapOf(
            "Kilo" to chartKilo,
            "Boy" to chartBoy,
            "Bel" to chartBel,
            "Kalça" to chartKalca,
            "Göğüs" to chartGogus
        )
    }

    private var tarihLabels = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grafik)
val tvDiyetisyen=findViewById<TextView>(R.id.tvDiyetisyen)
        spinnerGraphSelector = findViewById(R.id.spinnerGraphSelector)
        chartKilo = findViewById(R.id.chartKilo)
        chartBoy = findViewById(R.id.chartBoy)
        chartBel = findViewById(R.id.chartBel)
        chartKalca = findViewById(R.id.chartKalca)
        chartGogus = findViewById(R.id.chartGogus)
        tvDiyetisyen.setOnClickListener {
            var intent=Intent(this, DiyetisyenMapsActivity::class.java)
            startActivity(intent)

        }
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Başlangıçta tüm grafikler gizli olsun
        for (chart in charts.values) {
            chart.visibility = View.GONE
        }

        // Spinner seçenekleri
        val options = charts.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGraphSelector.adapter = adapter

        spinnerGraphSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val selected = options[position]
                showChart(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Seçim yoksa tüm grafikleri gizle
                for (chart in charts.values) {
                    chart.visibility = View.GONE
                }
            }
        }

        // Verileri getir ve grafiklere yükle
        fetchBodyDataFromFirestore()
    }

    private fun showChart(selectedName: String) {
        for ((name, chart) in charts) {
            chart.visibility = if (name == selectedName) View.VISIBLE else View.GONE
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

    private fun fetchBodyDataFromFirestore() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("bodyMeasurements")
            .get()
            .addOnSuccessListener { results ->
                val kiloEntries = mutableListOf<BarEntry>()
                val boyEntries = mutableListOf<BarEntry>()
                val belEntries = mutableListOf<BarEntry>()
                val kalcaEntries = mutableListOf<BarEntry>()
                val gogusEntries = mutableListOf<BarEntry>()
                val dates = mutableListOf<String>()

                results.documents
                    .sortedBy { it.getString("tarih") }
                    .forEachIndexed { index, doc ->
                        val date = doc.getString("tarih") ?: "Tarih Yok"
                        val weight = (doc.getDouble("kilo") ?: 0.0).toFloat()
                        val height = (doc.getDouble("boy") ?: 0.0).toFloat()
                        val waist = (doc.getDouble("bel") ?: 0.0).toFloat()
                        val hip = (doc.getDouble("kalça") ?: 0.0).toFloat()
                        val chest = (doc.getDouble("göğüs") ?: 0.0).toFloat()

                        kiloEntries.add(BarEntry(index.toFloat(), weight))
                        boyEntries.add(BarEntry(index.toFloat(), height))
                        belEntries.add(BarEntry(index.toFloat(), waist))
                        kalcaEntries.add(BarEntry(index.toFloat(), hip))
                        gogusEntries.add(BarEntry(index.toFloat(), chest))

                        dates.add(date)
                    }

                tarihLabels = dates

                setupBarChart(chartKilo, kiloEntries, "Kilo (kg)", tarihLabels)
                setupBarChart(chartBoy, boyEntries, "Boy (cm)", tarihLabels)
                setupBarChart(chartBel, belEntries, "Bel (cm)", tarihLabels)
                setupBarChart(chartKalca, kalcaEntries, "Kalça (cm)", tarihLabels)
                setupBarChart(chartGogus, gogusEntries, "Göğüs (cm)", tarihLabels)

                // İlk grafik otomatik görünür olsun
                showChart(charts.keys.first())
            }
            .addOnFailureListener {
                Toast.makeText(this, "Vücut ölçüleri alınamadı", Toast.LENGTH_SHORT).show()
            }
    }
}
