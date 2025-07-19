package com.example.fitnest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.enableEdgeToEdge

import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.reflect.typeOf


class VucutActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    private lateinit var cardMeasurements: CardView
    private lateinit var imgArrowMeasurements: ImageView
    private lateinit var contentMeasurements: LinearLayout
    private lateinit var btnSave: Button

    private lateinit var firestore: FirebaseFirestore

    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var etWaist: EditText
    private lateinit var etHip: EditText
    private lateinit var etChest: EditText
private lateinit var tvYag: TextView
private lateinit var tvBmi: TextView

    private lateinit var btnCalculateBmi: Button
    private lateinit var btnCalculateFat: Button
    private lateinit var btnSeeHistory: Button

    private lateinit var tvBmiResult: TextView
    private lateinit var tvFatResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vucut)
        cardMeasurements = findViewById(R.id.cardMeasurements)

        contentMeasurements = findViewById(R.id.contentMeasurements)
tvBmi=findViewById(R.id.tvBmi)
tvYag=findViewById(R.id.tvYag)
        etHeight = findViewById(R.id.etHeight)
        etWeight = findViewById(R.id.etWeight)
        etWaist = findViewById(R.id.etWaist)
        etHip = findViewById(R.id.etHip)
        etChest = findViewById(R.id.etChest)
val tvAcik=findViewById<TextView>(R.id.tvAcik)


         btnSave=findViewById(R.id.btnSave)

        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvFatResult = findViewById(R.id.tvFatResult)
        // Ölçüleri aç/kapa
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        tvAcik.setOnClickListener {
            var intent=Intent(this, SaglikRaporuActivity::class.java)
            startActivity(intent)
        }

   val imgInfoBmi=findViewById<ImageView>(R.id.imgInfoBmi)
        val imgInfoYag=findViewById<ImageView>(R.id.imgInfoYag)
        imgInfoBmi.setOnClickListener {
            Toast.makeText(this,"Vücut Kitle İndeksi (BMI), kilonuzun boyunuza göre değerlendirilmesidir. Sağlık durumu hakkında ipucu verir.",
                Toast.LENGTH_SHORT).show()
        }
        imgInfoYag.setOnClickListener {
            Toast.makeText(this,"Yağ oranı, vücudunuzdaki yağ miktarının toplam kilonuza oranıdır. Sağlık ve fitness seviyenizle ilgili bilgi verir.",
                Toast.LENGTH_SHORT).show()
        }


        btnSave.setOnClickListener {
            val height = etHeight.text.toString().toFloatOrNull()
            val weight = etWeight.text.toString().toFloatOrNull()
            val waist = etWaist.text.toString().toFloatOrNull()
            val hip = etHip.text.toString().toFloatOrNull()
            val chest = etChest.text.toString().toFloatOrNull()

            if (height == null || weight == null || waist == null || hip == null || chest == null) {
                Toast.makeText(this, "Lütfen tüm ölçü alanlarını doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val now = System.currentTimeMillis()
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val dateString = sdf.format(now)

            val userId = auth.currentUser?.uid
            if (userId != null) {
                val olcumData = hashMapOf<String, Any>(
                    "type" to "Vücut Ölçüleri",
                    "boy" to height,
                    "kilo" to weight,
                    "bel" to waist,
                    "kalça" to hip,
                    "göğüs" to chest,
                    "tarih" to dateString,
                    "timestamp" to com.google.firebase.Timestamp.now()
                )

                firestore.collection("users")
                    .document(userId)
                    .collection("bodyMeasurements")
                    .add(olcumData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Vücut ölçüleri başarıyla kaydedildi", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Vücut ölçüleri kaydedilirken sorun oluştu", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        tvBmi.setOnClickListener {
            val height = etHeight.text.toString().toFloatOrNull()
            val weight = etWeight.text.toString().toFloatOrNull()

            if (height == null || weight == null) {
                Toast.makeText(this, "Lütfen boy ve kilo giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val heightMeters = height / 100f
            val bmi = weight / (heightMeters * heightMeters)
            val bmiYorum = when {
                bmi < 18.5 -> "Zayıf\nVücut ağırlığınız idealin altında. Beslenme eksikliği veya başka sağlık sorunları olabilir."
                bmi < 25 -> "Normal (Sağlıklı)\nSağlıklı aralık. Formda bir vücut için uygun kilo."
                bmi < 30 -> "Fazla kilolu\nHafif şişmanlık. Risk artmaya başlar."
                bmi < 35 -> "Obez (1. derece)\nSağlık açısından riskli. Kilo kontrolü önerilir."
                bmi < 40 -> "İleri obez (2. derece)\nKalp, tansiyon, diyabet riski yüksektir."
                else -> "Morbid obez (3. derece)\nCiddi sağlık riski. Tıbbi destek gerekebilir."
            }

            tvBmiResult.text = "BMI: %.2f\n\n$bmiYorum".format(bmi)
            val now = System.currentTimeMillis()
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val dateString = sdf.format(now)
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val bmiData = hashMapOf<String, Any>(
                    "type" to "BMI",
                    "value" to bmi,           // BMI hesaplamasından gelen float/double değer
                    "comment" to bmiYorum,
                    "dateString" to dateString,// BMI'ye göre açıklama

                )

                firestore.collection("users")
                    .document(userId)
                    .collection("bmiMeasurements")
                    .add(bmiData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "BMI başarıyla kaydedildi", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "BMI kaydedilirken sorun oluştu", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

            // Yağ oranı hesapla butonu
            tvYag.setOnClickListener {
                val waist = etWaist.text.toString().toFloatOrNull()
                val hip = etHip.text.toString().toFloatOrNull()
                val height = etHeight.text.toString().toFloatOrNull()
                val weight = etWeight.text.toString().toFloatOrNull()

                if (waist == null || hip == null || hip == 0f || height == null || weight == null) {
                    Toast.makeText(this, "Lütfen tüm ölçüleri doğru giriniz", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                // Basit Vücut Yağ Oranı tahmini (bel/kalça oranına göre)
                val ratio = waist / hip
                val fatPercent = when {
                    ratio < 0.8 -> 15
                    ratio < 0.9 -> 20
                    else -> 25
                }

                val heightMeters = height / 100f
                val bmi = weight / (heightMeters * heightMeters)


                tvFatResult.text = "Yağ Oranı: %$fatPercent (Tahmini)"
                val now = System.currentTimeMillis()
                val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                val dateString = sdf.format(now)
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    val fatData = hashMapOf<String, Any>(
                        "type" to "Yağ Oranı",
                        "value" to fatPercent,
                        "dateString" to dateString
                    )
                    firestore.collection("users")
                        .document(userId)
                        .collection("fatMeasurements")
                        .add(fatData)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Yağ oranı başarıyla kaydedildi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Yağ oranı kaydedilirken sorun oluştu",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                }

            }


        }}}




