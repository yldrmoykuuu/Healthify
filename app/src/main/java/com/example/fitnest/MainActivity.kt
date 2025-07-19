package com.example.fitnest

import android.os.Bundle
import android.widget.Button
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

       val  tvRegisterMail=findViewById<TextView>(R.id.tvRegisterMail)
       val  tvRegisterSifre=findViewById<TextView>(R.id.tvRegisterSifre)
       val  tvRegisterSifreTekrar=findViewById<TextView>(R.id.tvRegisterSifreTekrar)
       val btnKayitOl=findViewById<Button>(R.id.btnKayitOl)


        btnKayitOl.setOnClickListener {
            if(tvRegisterMail.text.isNotEmpty()&&tvRegisterSifre.text.isNotEmpty()&&tvRegisterSifreTekrar.text.isNotEmpty())
            {if(tvRegisterSifre.text.toString().equals(tvRegisterSifreTekrar.text.toString())){
                yeniUyeKayit(tvRegisterMail.text.toString(),tvRegisterSifre.text.toString())
            }
                else{
                Toast.makeText(this,"Şifreler Aynı Değil", Toast.LENGTH_SHORT).show()
            }

            }else{
                Toast.makeText(this,"Lütfen Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show()
            }
        }}

    private fun yeniUyeKayit(mail: String, sifre: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,sifre).addOnCompleteListener(
            object :OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {if (p0.isSuccessful){
                    Toast.makeText(this@MainActivity,"Üye Başarıyla Kaydedildi", Toast.LENGTH_SHORT).show()
                }
                   else{
                    Toast.makeText(this@MainActivity,"Üye Kaydedilirken Sorun Oluştu", Toast.LENGTH_SHORT).show()
                }
                }

            })
    }
}






       