package com.example.fitnest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TarifListesiActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarifAdapter

    private val tarifList = listOf(

        // 1. Bordo Smoothie
        Tarif(
            "Kırmızı Meyveli Smoothie",
            "Enerji verici, lezzetli...",
            listOf("Çilek", "Muz", "Süt"),
            listOf("Malzemeleri blenderdan geçir.", "Soğuk servis et."),
            160,
            "https://image.hurimg.com/i/hurriyet/75/770x0/5acb22967af50708981b8d7d.jpg"
        ),

        // 2. Yeşil Detox Smoothie
        Tarif(
            "Yeşil Detox Smoothie",
            "Vücudu arındırır ve tazeler.",
            listOf("Ispanak", "Yeşil elma", "Salatalık", "Limon suyu"),
            listOf("Tüm malzemeleri blenderdan geçir.", "Buzla servis et."),
            120,
            "https://abspace.yves-rocher.com/tr/wp-content/uploads/sites/17/2017/10/1801_smoothie_Image1_750x552.jpg"
        ),

        // 3. Chia Puding
        Tarif(
            "Chia Puding",
            "Tok tutar, omega 3 deposu.",
            listOf("Chia tohumu", "Badem sütü", "Bal", "Çilek"),
            listOf("Chia tohumlarını badem sütüyle karıştır.", "Buzdolabında 4 saat beklet.", "Meyve ile süsle."),
            180,
            "https://www.durupazar.com/idea/bi/33/myassets/blogs/blog-279.jpg?revision=1736162755"
        ),

        // 4. Avokado Salatası
        Tarif(
            "Avokado Salatası",
            "Sağlıklı yağlar ve vitaminler içerir.",
            listOf("Avokado", "Domates", "Salatalık", "Zeytinyağı", "Limon"),
            listOf("Malzemeleri doğrayıp karıştır.", "Zeytinyağı ve limon ekle."),
            200,
            "https://i.lezzet.com.tr/images-xxlarge-recipe/avokadolu-domates-salatasi-fe93a72e-72e5-4eba-8ec9-01c5a3b44f4d.jpg"
        ),

        // 5. Yulaf Bowl
        Tarif(
            "Yulaf Bowl",
            "Güne enerji dolu başlatır.",
            listOf("Yulaf", "Süt", "Muz", "Badem", "Bal"),
            listOf("Yulafı sütle pişir.", "Üzerine muz ve badem ekle, bal gezdir."),
            250,
            "https://www.sneakscloud.com/minio-url/blog/chia-bowl-nasil-yapilir_5f1ee2886104c.jpg"
        ),

        // 6. Zencefilli Limonata
        Tarif(
            "Zencefilli Limonata",
            "Bağışıklığı güçlendirir.",
            listOf("Limon", "Taze zencefil", "Bal", "Su"),
            listOf("Limon ve zencefili karıştır.", "Bal ve su ekleyip buzla servis et."),
            90,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmQl4JTj-cyFhO6SDIlIAIkrdVmImLozAVYQ&s"
        ),

        // 7. Muzlu Fıstık Ezmesi Tostu
        Tarif(
            "Muzlu Fıstık Ezmesi Tostu",
            "Tatlı isteğini sağlıklı bastır.",
            listOf("Tam buğday ekmeği", "Fıstık ezmesi", "Muz", "Tarçın"),
            listOf("Ekmeğe fıstık ezmesi sür.", "Üzerine muz dilimleri koy ve tarçın serp."),
            220,
            "https://media.istockphoto.com/id/820269468/tr/foto%C4%9Fraf/muzlu-f%C4%B1st%C4%B1k-ezmeli-tost-sandvi%C3%A7-bal-ile-ah%C5%9Fap-plaka-%C3%BCzerinde-f%C4%B1st%C4%B1k-ezmesi-muz-dilimi-ve.jpg?s=1024x1024&w=is&k=20&c=2LBdbY8o0DMPCOWmm3Gw6hRF-CTHuMT7YOGQ_YaKdLE="
        ),

        // 8. Havuçlu Humus
        Tarif(
            "Havuçlu Humus",
            "Sağlıklı atıştırmalık.",
            listOf("Nohut", "Havuç", "Tahini", "Zeytinyağı", "Limon suyu"),
            listOf("Tüm malzemeleri rondoda çek.", "Sebzelerle servis et."),
            170,
            "https://static.daktilo.com/sites/1638/uploads/2024/04/15/128676-1.jpg"
        ),

        // 9. Yoğurtlu Meyve Kasesi
        Tarif(
            "Yoğurtlu Meyve Kasesi",
            "Tatlı ve serinletici.",
            listOf("Yoğurt", "Kivi", "Muz", "Ceviz", "Bal"),
            listOf("Tüm malzemeleri bir kaseye al ve bal gezdir."),
            180,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT75PPBCtXCYURAY5wHmyVPLka1EwL-xAc1Xw&s"
        ),

        // 10. Sebzeli Omlet
        Tarif(
            "Sebzeli Omlet",
            "Protein ve vitamin deposu.",
            listOf("Yumurta", "Ispanak", "Domates", "Biber", "Zeytinyağı"),
            listOf("Sebzeleri sotele.", "Yumurtayı ekle ve pişir."),
            210,
            "https://www.acibadem.com.tr/hayat/Images/YayinTarifler/sebzeli-omlet_1008_1.jpg"
        ),

        // 11. Karpuz Nane Smoothie
        Tarif(
            "Karpuz Nane Smoothie",
            "Yaz için serinletici.",
            listOf("Karpuz", "Nane", "Lime suyu"),
            listOf("Blenderdan geçir, buz ekle."),
            110,
            "https://i0.wp.com/www.kocaelifikir.com/wp-content/uploads/2025/06/5d10d1bec03c0e196c638b1a.webp"
        ),

        // 12. Elmalı Tarçınlı Yulaf
        Tarif(
            "Elmalı Tarçınlı Yulaf",
            "Tatlı krizine birebir.",
            listOf("Yulaf", "Elma", "Tarçın", "Süt"),
            listOf("Yulafı sütle pişir.", "Üzerine elma ve tarçın ekle."),
            230,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToZJ-wvLQL5vWS46HqdUh5dLht9vjzaauUkQ&s"
        ),

        // 13. Ananas-Kiwi Smoothie
        Tarif(
            "Ananas-Kiwi Smoothie",
            "Egzotik ve ferahlatıcı.",
            listOf("Ananas", "Kiwi", "Hindistan cevizi suyu"),
            listOf("Blenderdan geçir, soğuk servis."),
            135,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSal653giEFbXwZTP4mCjj0tqWjTAbXVcniqQ&s"
        ),

        // 14. Mercimek Salatası
        Tarif(
            "Mercimek Salatası",
            "Lif ve protein kaynağı.",
            listOf("Yeşil mercimek", "Domates", "Maydanoz", "Zeytinyağı", "Limon"),
            listOf("Mercimeği haşla.", "Diğer malzemelerle karıştır."),
            190,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT442VVWU4WRtf9SPp2jImzRzheFe4GoKoqwg&s"
        ),

        // 15. Smoothie Bowl
        Tarif(
            "Smoothie Bowl",
            "Tatlı ve doyurucu bir seçenek.",
            listOf("Orman meyveleri", "Muz", "Yoğurt", "Granola"),
            listOf("Blenderdan geçir.", "Kaselere koy ve granola ile süsle."),
            200,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjHlJfVVuBg6xIkuhNB5bgb93X33zWv6nwgg&s"
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarif_listesi)
        recyclerView = findViewById(R.id.recyclerTarifler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TarifAdapter(tarifList) { tarif ->
            // Detay Activity aç
            val intent = Intent(this, TarifDetayActivity::class.java)

            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }
    }

