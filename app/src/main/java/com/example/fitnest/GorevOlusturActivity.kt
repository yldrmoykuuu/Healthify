package com.example.fitnest

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.widget.*
import androidx.activity.enableEdgeToEdge

import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.text.SimpleDateFormat
import java.util.*

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class GorevOlusturActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var spinnerType: Spinner
    private lateinit var etDescription: EditText
    private lateinit var tvDate: TextView
    private lateinit var btnAddReminder: Button
    private lateinit var calendarView: MaterialCalendarView
    private lateinit var tvEvents: TextView
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gorev_olustur)
        calendarView = findViewById(R.id.calendarView)
        tvEvents = findViewById(R.id.tvEvents)
        spinnerType = findViewById(R.id.spinnerType)
        etDescription = findViewById(R.id.etDescription)
        tvDate = findViewById(R.id.tvDate)
        btnAddReminder = findViewById(R.id.btnAddReminder)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val types = listOf("Hastane", "Diyetisyen", "Eczane", "GYM", "Diğer")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        spinnerType.adapter = adapter

        tvDate.setOnClickListener {
            val calendar = android.icu.util.Calendar.getInstance()
            val year = calendar.get(android.icu.util.Calendar.YEAR)
            val month = calendar.get(android.icu.util.Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = String.format("%02d/%02d/%d", d, m + 1, y)
                tvDate.text = selectedDate
            }, year, month, day)

            datePicker.show()
        }
        btnAddReminder.setOnClickListener {
            val type = spinnerType.selectedItem.toString()
            val description = etDescription.text.toString()
            if (description.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            val hatirlaticiData = hashMapOf<String, Any>(
                "type " to "Hatırlatıcı",
                "description" to description,
                "date" to selectedDate,
                "timastamp" to Timestamp.now()
            )
            firestore.collection("users")
                .document(userId)
                .collection("reminders")
                .add(hatirlaticiData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Hatırlatma eklendi", Toast.LENGTH_SHORT).show()
                    finish() // Sayfadan çık
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Hata oluştu", Toast.LENGTH_SHORT).show()
                }
        }

        calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            val selectedDate = String.format("%02d/%02d/%04d", date.day, date.month + 1, date.year)
            val userId = auth.currentUser?.uid ?: return@OnDateSelectedListener

            calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
                val selectedDate =
                    String.format("%02d/%02d/%04d", date.day, date.month + 1, date.year)
                val userId = auth.currentUser?.uid ?: return@OnDateSelectedListener

                firestore.collection("users")
                    .document(userId)
                    .collection("reminders")
                    .whereEqualTo("date", selectedDate)
                    .get()
                    .addOnSuccessListener { reminderResult ->
                        if (!reminderResult.isEmpty) {
                            val reminderTexts = reminderResult.mapNotNull { doc ->
                                val type = doc.getString("type") ?: "Görev:"
                                val desc = doc.getString("description") ?: ""
                                "📌 $type: $desc"
                            }
                            val fullText = "🔔 Hatırlatmalar:\n" + reminderTexts.joinToString("\n")
                            tvEvents.text = fullText
                        } else {
                            tvEvents.text = "🔔 Hatırlatma bulunamadı."
                        }
                    }
                    .addOnFailureListener {
                        tvEvents.text = "Hatırlatmalar alınamadı."
                    }
            })

    })
}}
