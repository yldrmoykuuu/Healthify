package com.example.fitnest

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class SaglikActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sharedPrefs: SharedPreferences

    private val imageViews = mutableListOf<ImageView>()
    private var currentFilledCount = 0
    private val maxCount = 10
    private val litrePerGlass = 0.25f
    private var totalLitres = 0f
    private var currentSleep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_saglik)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        sharedPrefs = getSharedPreferences("HealthPrefs", MODE_PRIVATE)

        val tvWaterValue = findViewById<TextView>(R.id.tvWaterValue)
        val tvSleepHours = findViewById<TextView>(R.id.tvSleepHours)
        val seekBarSleep = findViewById<SeekBar>(R.id.seekBarSleep)
        val btnKaydet = findViewById<Button>(R.id.btnKaydetSaglik)
        val tvSaglikDegisimi = findViewById<TextView>(R.id.tvSaglikDegisimi)

        // Su bardaki imageView'leri listeye al
        imageViews.addAll(
            listOf(
                findViewById(R.id.img1), findViewById(R.id.img2), findViewById(R.id.img3),
                findViewById(R.id.img4), findViewById(R.id.img5), findViewById(R.id.img6),
                findViewById(R.id.img7), findViewById(R.id.img8), findViewById(R.id.img9),
                findViewById(R.id.img10)
            )
        )

        currentFilledCount = sharedPrefs.getInt("filledCount", 0)
        currentSleep = sharedPrefs.getInt("sleepHours", 0)
        totalLitres = currentFilledCount * litrePerGlass

        tvWaterValue.text = String.format("%.2f Litre", totalLitres)
        tvSleepHours.text = "Uyku Süresi: $currentSleep saat"
        seekBarSleep.progress = currentSleep

        // Başlangıçta bardaki doluluk durumunu göster
        for ((index, img) in imageViews.withIndex()) {
            if (index < currentFilledCount) {
                img.setImageResource(R.drawable.dolusu)
                img.tag = "filled"
            } else {
                img.setImageResource(R.drawable.su)
                img.tag = "empty"
            }

            img.setOnClickListener {
                val isFilled = img.tag == "filled"

                img.animate().scaleX(1.1f).scaleY(1.1f).setDuration(150).withEndAction {
                    img.animate().scaleX(1f).scaleY(1f).duration = 150
                }

                if (!isFilled && index == currentFilledCount && currentFilledCount < maxCount) {
                    img.setImageResource(R.drawable.dolusu)
                    img.tag = "filled"
                    currentFilledCount++
                } else if (isFilled && index == currentFilledCount - 1) {
                    img.setImageResource(R.drawable.su)
                    img.tag = "empty"
                    currentFilledCount--
                }

                totalLitres = currentFilledCount * litrePerGlass
                tvWaterValue.text = String.format("%.2f Litre", totalLitres)
                sharedPrefs.edit().putInt("filledCount", currentFilledCount).apply()
            }
        }

        seekBarSleep.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentSleep = progress
                tvSleepHours.text = "Uyku Süresi: $progress saat"
                sharedPrefs.edit().putInt("sleepHours", progress).apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnKaydet.setOnClickListener {
            saveHealthDataToFirestore(currentSleep, totalLitres)
        }

        tvSaglikDegisimi.setOnClickListener {
            val intent = Intent(this, SaglikDetayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveHealthDataToFirestore(sleepHours: Int, waterLitres: Float) {
        val userId = auth.currentUser?.uid ?: return
        val now = System.currentTimeMillis()
        val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(now)

        val healthData = hashMapOf<String, Any>(
            "sleepHours" to sleepHours,
            "waterLitres" to waterLitres,
            "date" to dateString,
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        firestore.collection("users")
            .document(userId)
            .collection("healthLogs")
            .add(healthData)
            .addOnSuccessListener {
                Toast.makeText(this, "Veriler Firebase'e kaydedildi", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Hata oluştu", Toast.LENGTH_SHORT).show()
            }
    }
}
