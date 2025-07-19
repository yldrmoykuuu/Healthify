package com.example.fitnest

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class ProfilActivity : AppCompatActivity() {
    private lateinit var imgProfile: ImageView


    // Fotoğraf seçmek için launcher
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                // URI'den inputStream aç
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                // ImageView'a bitmap set et
                imgProfile.setImageBitmap(bitmap)

                // Fotoğrafı dahili dosyaya kopyala ve yolu kaydet
                val filePath = copyUriToInternalStorage(it, "profile_image.jpg")
                if (filePath.isNotEmpty()) {
                    val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                    prefs.edit().putString("image_path", filePath).apply()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Fotoğraf yüklenirken hata oluştu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profil)
        val cikis=findViewById<TextView>(R.id.cikis)
        cikis.setOnClickListener {
            var intent=Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        imgProfile = findViewById(R.id.imgProfile)

        // Daha önce kaydedilmiş dosya yolundan fotoğrafı yükle
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val savedPath = prefs.getString("image_path", null)
        savedPath?.let {
            val bitmap = BitmapFactory.decodeFile(it)
            if (bitmap != null) {
                imgProfile.setImageBitmap(bitmap)
            }
        }

        // ImageView tıklanma olayı
        imgProfile.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Diğer view'lar
        val shareapp = findViewById<TextView>(R.id.shareapp)
        val tvAboutUs = findViewById<TextView>(R.id.tvAboutUs)
        val tvFeedBack = findViewById<TextView>(R.id.tvFeedBack)

        tvAboutUs.setOnClickListener {
            showAboutDialog()
        }

        tvFeedBack.setOnClickListener {
            showFeedDialog()
        }

        shareapp.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Bu harika uygulamayı indir: https://play.google.com/store/apps/details?id=com.orneksirket.ornekuygulama"
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Uygulamayı paylaş"))
        }
    }

    private fun copyUriToInternalStorage(uri: Uri, filename: String): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return ""
            val file = File(filesDir, filename)
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Hakkımızda")
            .setMessage(
                "FitNest uygulamasına hoş geldiniz!\n\n" +
                        "Bu uygulama, kullanıcıların sağlıklı yaşam ve spor aktivitelerini kolayca takip edebilmeleri için ÖYKÜ YILDIRIM tarafından geliştirilmiştir.\n\n" +
                        "Özellikler:\n" +
                        "- Profil yönetimi\n" +
                        "- Aktivite takibi\n" +
                        "- İlerleme grafikleri\n" +
                        "- Geri bildirim ve destek\n\n" +
                        "Versiyon: 1.0.0\n" +
                        "© 2025 Tüm hakları saklıdır.\n\n" +
                        "Sorularınız veya önerileriniz için bizimle iletişime geçebilirsiniz."
            )
            .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showFeedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Destek ve Geri Bildirim")

        val input = EditText(this).apply {
            hint = "Geri bildiriminizi yazınız"
            minLines = 3
            maxLines = 5
            setLines(4)
            isSingleLine = false
            gravity = android.view.Gravity.TOP or android.view.Gravity.START
            setPadding(16, 16, 16, 16)
        }

        builder.setView(input)

        builder.setPositiveButton("Gönder") { dialog, _ ->
            val feedback = input.text.toString()
            if (feedback.isNotBlank()) {
                sendFeedbackEmail(feedback)
                Toast.makeText(this, "Geri bildiriminiz gönderildi, teşekkürler!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Lütfen geri bildirim yazınız.", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("İptal") { dialog, _ -> dialog.dismiss() }

        builder.show()
    }

    private fun sendFeedbackEmail(feedback: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("destek@ornek.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Uygulama Geri Bildirimi")
            putExtra(Intent.EXTRA_TEXT, feedback)
        }
        try {
            startActivity(Intent.createChooser(intent, "E-posta ile Gönder"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "E-posta uygulaması bulunamadı.", Toast.LENGTH_SHORT).show()
        }
    }
}
