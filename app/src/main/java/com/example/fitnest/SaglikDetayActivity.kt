package com.example.fitnest

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SaglikDetayActivity : AppCompatActivity() {
    private lateinit var tvSuOrtalama: TextView
    private lateinit var tvUykuOrtalama: TextView
    private lateinit var barChartSu: BarChart
    private lateinit var barChartUyku: BarChart
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_saglik_detay)
        tvSuOrtalama = findViewById(R.id.tvSuOrtalama)
        tvUykuOrtalama = findViewById(R.id.tvUykuOrtalama)
        barChartUyku = findViewById(R.id.chartUyku)
        barChartSu = findViewById(R.id.chartSuTuketimi) // Bu artık BarChart
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        fetchHealthDataFromFirestore()
    }

    private fun fetchHealthDataFromFirestore() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("healthLogs")
            .get()
            .addOnSuccessListener { result ->
                val suVerisi = mutableListOf<BarEntry>()
                val suLabels = mutableListOf<String>()
                val uykuVerisi = mutableListOf<BarEntry>()
                val uykuLabels = mutableListOf<String>()

                val sortedDocs = result.documents.sortedBy { it.getString("date") }

                sortedDocs.forEachIndexed { index, doc ->
                    val date = doc.getString("date") ?: "Tarih Yok"
                    val su = (doc.getDouble("waterLitres") ?: 0.0).toFloat()
                    val uyku = (doc.getDouble("sleepHours") ?: 0.0).toFloat()

                    if (su > 0f) {
                        suVerisi.add(BarEntry(index.toFloat(), su))
                        suLabels.add(date)
                    }
                    if (uyku > 0f) {
                        uykuVerisi.add(BarEntry(index.toFloat(), uyku))
                        uykuLabels.add(date)
                    }

                }
                val suOrtalama = if (suVerisi.isNotEmpty()) suVerisi.map { it.y }.average().toFloat() else 0f
                val uykuOrtalama = if (uykuVerisi.isNotEmpty()) uykuVerisi.map { it.y }.average().toFloat() else 0f

                // TextView'lere yazdır
                tvSuOrtalama.text = "Su Ortalaması: %.2f L".format(suOrtalama)
                tvUykuOrtalama.text = "Uyku Ortalaması: %.2f Saat".format(uykuOrtalama)
                showCharts(suVerisi, suLabels, uykuVerisi, uykuLabels)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Veriler alınamadı", Toast.LENGTH_SHORT).show()
            }

    }


    private fun showCharts(
        suVerisi: List<BarEntry>,
        suLabels: List<String>,
        uykuVerisi: List<BarEntry>,
        uykuLabels: List<String>
    ) {
        // BarChart - Su Tüketimi
        val suDataSet = BarDataSet(suVerisi, "Su Tüketimi (Litre)")
        suDataSet.color = Color.parseColor("#3F51B5")
        suDataSet.valueTextSize = 14f
        val suBarData = BarData(suDataSet)
        barChartSu.data = suBarData
        barChartSu.description.isEnabled = false
        barChartSu.setFitBars(true)
        barChartSu.xAxis.valueFormatter = IndexAxisValueFormatter(suLabels)
        barChartSu.xAxis.granularity = 1f
        barChartSu.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChartSu.axisRight.isEnabled = false
        barChartSu.axisLeft.axisMinimum = 0f
        barChartSu.invalidate()

        // BarChart - Uyku Süresi (Mevcut kodun aynısı)
        val uykuDataSet = BarDataSet(uykuVerisi, "Uyku Süresi (Saat)")
        uykuDataSet.color = Color.parseColor("#3F51B5")
        uykuDataSet.valueTextSize = 14f
        val uykuBarData = BarData(uykuDataSet)
        barChartUyku.data = uykuBarData
        barChartUyku.description.isEnabled = false
        barChartUyku.setFitBars(true)
        barChartUyku.xAxis.valueFormatter = IndexAxisValueFormatter(uykuLabels)
        barChartUyku.xAxis.granularity = 1f
        barChartUyku.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChartUyku.axisRight.isEnabled = false
        barChartUyku.axisLeft.axisMinimum = 0f
        barChartUyku.invalidate()
    }
}
