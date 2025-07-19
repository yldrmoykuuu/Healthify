package com.example.fitnest

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ana_main)
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val tvGun = findViewById<TextView>(R.id.tvGun)
val tvGorev=findViewById<TextView>(R.id.tvGorev)
        val tvSpor = findViewById<TextView>(R.id.tvSpor)
        val tvVucut=findViewById<TextView>(R.id.tvVucut)
val tvKiloDegisimi=findViewById<TextView>(R.id.tvSaglikDegisimi)
        val tvSaglik = findViewById<TextView>(R.id.tvSaglik)
        val recyclerView = findViewById<RecyclerView>(R.id.rvArticles)


        val sharedPref = getSharedPreferences("KiloBoyVerisi", MODE_PRIVATE)

// SharedPreferences'tan verileri al
        val hedefKilo = sharedPref.getFloat("hedefKilo", 0f)
        val etHeight = sharedPref.getFloat("yeniKilo", 0f)
         // TextView'ı burada alıyoruz
tvKiloDegisimi.setOnClickListener {
    var intent= Intent(this, GrafikActivity::class.java)
    startActivity(intent)
}


        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val articleList = listOf(
            Article(
                "Sağlıklı Beslenme Rehberi",
                R.drawable.yemekhazir,
                "Sağlıklı ve dengeli beslenme, yaşam kalitesini yükselten, kronik hastalıklara karşı koruma sağlayan ve beden-zihin sağlığını destekleyen temel bir yaşam alışkanlığıdır. Dünya Sağlık Örgütü (DSÖ) ve pek çok beslenme otoritesi, sağlıklı beslenmenin öncelikli unsurlarını belirlemiş ve bu prensiplerin günlük hayatta uygulanmasını önermektedir.\n\n" +

                        "1. **Besin Gruplarının Dengeli Tüketimi**\n" +
                        "- **Karbonhidratlar:** Enerji sağlamak için ana kaynaktır. Kompleks karbonhidratlar (tam tahıllar, baklagiller) kan şekerini dengede tutar ve sindirim sistemi sağlığını destekler.\n" +
                        "- **Proteinler:** Kas onarımı, bağışıklık fonksiyonları ve hormon sentezi için gereklidir. Hayvansal (et, süt, yumurta) ve bitkisel (baklagiller, kuruyemişler) kaynaklardan dengeli şekilde alınmalıdır.\n" +
                        "- **Yağlar:** Hücre zarının yapısı, hormon üretimi ve yağda eriyen vitaminlerin emilimi için elzemdir. Özellikle omega-3 ve omega-6 gibi doymamış yağ asitleri içeren kaynaklar (balık, zeytinyağı, ceviz) tercih edilmelidir.\n" +
                        "- **Vitamin ve Mineraller:** Antioksidan özellikleriyle bağışıklığı destekler, kemik sağlığı, sinir sistemi ve metabolizma fonksiyonları için gereklidir. Renkli sebze ve meyveler, yeşil yapraklılar, süt ürünleri ve kuruyemişler zengin kaynaklardır.\n\n" +

                        "2. **İşlenmiş ve Rafine Gıdalardan Kaçınma**\n" +
                        "- Rafine şeker ve tuz içeren, katkı maddeleriyle dolu işlenmiş gıdalar; inflamasyonu artırır, insülin direnci ve obezite riskini yükseltir.\n" +
                        "- İşlenmiş gıdaların sık tüketimi, kalp-damar hastalıkları, diyabet ve bazı kanser türleriyle ilişkilendirilmiştir.\n\n" +

                        "3. **Lifli Gıdaların Önemi**\n" +
                        "- Lif, bağırsak sağlığını destekler, kan şekeri ve kolesterol seviyelerini dengeler.\n" +
                        "- Tam tahıllar, sebzeler, meyveler ve baklagiller önemli lif kaynaklarıdır.\n\n" +

                        "4. **Su Tüketimi ve Hidratasyon**\n" +
                        "- Su, vücudun tüm biyokimyasal reaksiyonlarında rol oynar ve toksinlerin atılımını sağlar.\n" +
                        "- Yetersiz su tüketimi; baş ağrısı, yorgunluk, konsantrasyon bozukluğu gibi problemlere yol açabilir.\n" +
                        "- Günlük ortalama 2-3 litre su tüketimi önerilmektedir, ancak yaş, aktivite düzeyi ve iklim koşullarına göre değişiklik gösterebilir.\n\n" +

                        "5. **Porsiyon Kontrolü ve Öğün Düzeni**\n" +
                        "- Aşırı kalori alımı kilo artışına neden olurken, yetersiz kalori alımı beslenme yetersizliklerine yol açar.\n" +
                        "- Öğün atlamamak, düzenli ve dengeli beslenme alışkanlıkları için önemlidir.\n" +
                        "- Küçük ve sık öğünler metabolizmanın dengeli çalışmasına yardımcı olabilir.\n\n" +

                        "6. **Özel Dönemlerde Beslenme**\n" +
                        "- Hamilelik, emzirme, çocukluk, yaşlılık gibi özel dönemlerde beslenme ihtiyaçları farklılık gösterir.\n" +
                        "- Bu dönemlerde yeterli kalori ve mikrobesin alımına dikkat edilmeli, gerekirse uzman desteği alınmalıdır.\n\n" +

                        "7. **Kronik Hastalıkların Önlenmesi ve Yönetimi**\n" +
                        "- Sağlıklı beslenme, diyabet, hipertansiyon, kalp hastalıkları ve obezitenin önlenmesinde kritik rol oynar.\n" +
                        "- Akdeniz diyeti gibi bitkisel ağırlıklı, doymamış yağ ve lif açısından zengin diyetlerin hastalık riskini azalttığı bilimsel olarak kanıtlanmıştır.\n\n" +

                        "8. **Psikolojik ve Sosyal Yönler**\n" +
                        "- Beslenme alışkanlıkları ruh hali ve bilişsel fonksiyonlarla yakından ilişkilidir.\n" +
                        "- Dengeli beslenme enerji seviyesini artırır, depresyon ve anksiyete riskini azaltır.\n" +
                        "- Aile ve toplum içinde paylaşılan sağlıklı beslenme alışkanlıkları sosyal bağları güçlendirir.\n\n" +

                        "9. **Sürdürülebilirlik ve Çevresel Etki**\n" +
                        "- Sağlıklı beslenme aynı zamanda çevresel sürdürülebilirliği destekler.\n" +
                        "- Bitkisel bazlı, yerel ve mevsimlik ürünlerin tercih edilmesi karbon ayak izini azaltır.\n\n" +

                        "Sonuç olarak, sağlıklı beslenme yaşam kalitesini artıran, hastalıklardan koruyan ve uzun ömür sağlayan çok boyutlu bir yaklaşımdır. Bilimsel veriler ışığında, bireylerin beslenme alışkanlıklarını bilinçli şekilde düzenlemesi ve sürdürülebilir tercihler yapması önem taşır."
            ),

                    Article(
                "Spor Yapmanın Faydaları",
                R.drawable.hareket,
                "Spor yapmak, sağlıklı yaşamın temel taşlarından biridir ve sadece fiziksel görünümü iyileştirmekle kalmaz; genel sağlık üzerinde de kapsamlı faydalar sağlar.\n\n" +

                        "1. **Kalp-Damar Sağlığı:**\n" +
                        "- Düzenli egzersiz, kalp kasını güçlendirir, kan dolaşımını iyileştirir ve yüksek tansiyon riskini azaltır.\n" +
                        "- LDL (kötü kolesterol) seviyelerini düşürürken HDL (iyi kolesterol) seviyelerini artırır, böylece kalp hastalıklarına karşı koruma sağlar.\n\n" +

                        "2. **Kas ve Kemik Sağlığı:**\n" +
                        "- Spor, kas kütlesini ve kuvvetini artırarak günlük hareket kabiliyetini yükseltir.\n" +
                        "- Ağırlık taşıyan egzersizler kemik yoğunluğunu artırır ve osteoporoz riskini azaltır.\n\n" +

                        "3. **Metabolik Sağlık ve Kilo Kontrolü:**\n" +
                        "- Egzersiz metabolizmayı hızlandırır, yağ yakımını destekler ve kilo kontrolünü kolaylaştırır.\n" +
                        "- Düzenli spor yapanlarda tip 2 diyabet, metabolik sendrom ve obezite riski azalır.\n\n" +

                        "4. **Bağışıklık Sistemi:**\n" +
                        "- Orta düzeyde egzersiz bağışıklık hücrelerinin etkinliğini artırarak enfeksiyonlara karşı koruma sağlar.\n" +
                        "- Kronik hastalıklara karşı direnci güçlendirir.\n\n" +

                        "5. **Zihinsel ve Duygusal Sağlık:**\n" +
                        "- Spor yaparken salınan endorfin ve serotonin gibi nörotransmitterler mutluluk ve huzur duygusunu artırır.\n" +
                        "- Stres, anksiyete ve depresyon belirtilerini azaltır.\n" +
                        "- Bilişsel fonksiyonları destekleyerek odaklanma, hafıza ve öğrenme kapasitesini geliştirir.\n\n" +

                        "6. **Uyku Kalitesi:**\n" +
                        "- Düzenli fiziksel aktivite, uyku düzenini iyileştirir ve derin uyku süresini artırır.\n\n" +

                        "7. **Sosyal ve Psikolojik Faydalar:**\n" +
                        "- Grup egzersizleri, sosyal bağları güçlendirir, aidiyet duygusu yaratır.\n" +
                        "- Özgüveni artırır, kendini ifade etme ve kişisel disiplin kazandırır.\n\n" +

                        "8. **Enerji Seviyesi ve Dayanıklılık:**\n" +
                        "- Spor yapmak, günlük enerji seviyesini yükseltir ve yorgunluk hissini azaltır.\n" +
                        "- Dayanıklılık kapasitesini artırarak günlük aktivitelerin daha kolay yapılmasını sağlar.\n\n" +

                        "Sonuç olarak, spor yapmak bedensel sağlığı güçlendirirken zihinsel ve ruhsal dengeyi de destekler. Sağlıklı ve uzun bir yaşam için düzenli fiziksel aktivite vazgeçilmezdir. Her yaş ve kondisyon seviyesine uygun spor aktiviteleriyle başlayarak yaşam kalitenizi artırabilirsiniz."
            ),

                    Article(
                "Aralıklı Oruç ve Metabolik Sendrom: Sistematik Derlemeler Ne Diyor?",
                R.drawable.aralikli,
                "Aralıklı oruç (intermittent fasting), belirli zaman dilimlerinde beslenmeye izin verilip diğer zamanlarda kalori alımının kısıtlandığı beslenme modelidir. Bu yöntem, kilo kontrolü ve metabolik sağlık üzerinde olumlu etkiler göstermesi sebebiyle son yıllarda popülerlik kazanmıştır.\n\n" +

                        "Sistematik derlemeler, aralıklı orucun metabolik sendrom üzerindeki etkilerini çeşitli açılardan incelemiştir. Metabolik sendrom; insülin direnci, abdominal obezite, yüksek tansiyon, yüksek trigliserid ve düşük HDL kolesterol gibi risk faktörlerinin bir arada bulunmasıdır ve kalp-damar hastalıkları için önemli bir risk teşkil eder.\n\n" +

                        "1. Vücut Ağırlığı ve Yağ Kütlesi Azalması\n" +
                        "- Aralıklı oruç uygulayan bireylerde yapılan çalışmalar, özellikle bel çevresinde anlamlı yağ kaybı ve vücut ağırlığında azalma olduğunu göstermektedir.\n" +
                        "- Bu yağ kaybı, metabolik sendromun temel problemlerinden biri olan abdominal obezitenin iyileşmesine katkı sağlar.\n\n" +

                        "2. İnsülin Direncinin Azalması\n" +
                        "- Aralıklı oruç, insülin hassasiyetini artırarak kan şekeri kontrolünü iyileştirir.\n" +
                        "- Bu sayede tip 2 diyabet riskinin azalması beklenir.\n\n" +

                        "3. Lipid Profili ve Kan Basıncı Üzerindeki Etkiler\n" +
                        "- Trigliserid düzeylerinde düşüş gözlemlenmiş, HDL kolesterolde ise hafif artışlar rapor edilmiştir.\n" +
                        "- Bazı çalışmalar, kan basıncının düzenlenmesinde de olumlu etkiler bildirmiştir.\n\n" +

                        "4. Otofaji ve Hücresel Onarım\n" +
                        "- Aralıklı oruç, hücrelerde hasarlı protein ve organellerin temizlenmesini sağlayan otofaji sürecini tetikleyerek hücresel yenilenmeyi destekler.\n" +
                        "- Bu mekanizma, kronik hastalıkların önlenmesinde önemli rol oynayabilir.\n\n" +

                        "5. Uzun Vadeli Etkiler ve Bilimsel Değerlendirme\n" +
                        "- Aralıklı orucun kısa ve orta vadede metabolik faydaları sistematik derlemelerde güçlü biçimde desteklenmekle beraber,\n" +
                        "- Uzun vadeli etkinliği ve güvenliği için daha kapsamlı, kontrollü klinik çalışmalara ihtiyaç vardır.\n" +
                        "- Ayrıca, herkes için uygun olmayabileceği, özellikle hamile, emziren, kronik hastalığı olan veya yeme bozukluğu geçmişi bulunan bireylerin doktor kontrolünde uygulaması önerilir.\n\n" +

                        "Sonuç olarak, aralıklı oruç metabolik sendromun risk faktörlerini azaltmada umut vadeden bir beslenme modeli olarak görülmektedir. Ancak, bireysel farklılıklar ve yaşam tarzı koşulları dikkate alınarak bilinçli ve dengeli şekilde uygulanmalıdır."
            ),

                    Article(
                "Uyku Kalitesi Artırma",
                R.drawable.uyku,
                "Uyku, fiziksel ve zihinsel sağlığımızın temel taşlarından biridir. Yeterli ve kaliteli uyku, bağışıklık sisteminin güçlenmesini sağlar, öğrenme ve hafıza fonksiyonlarını destekler, metabolizmayı düzenler ve duygusal dengeyi korur. Uyku problemleri ise yorgunluk, odaklanma bozukluğu, stres, bağışıklık düşüklüğü gibi pek çok olumsuz etkiye yol açabilir.\n\n" +

                        "1. Uyku Döngüsü ve Kalitesi\n" +
                        "- Uyku, REM (Hızlı Göz Hareketleri) ve NREM (Hızlı Olmayan Göz Hareketleri) olmak üzere farklı evrelerden oluşur. Bu evrelerin düzenli ve yeterli sürelerle tamamlanması kaliteli uyku için kritiktir.\n" +
                        "- Uyku kalitesi; uykunun derinliği, bölünmeden geçen süresi, uyku başlangıç süresi gibi kriterlerle değerlendirilir.\n\n" +

                        "2. Uyku Ortamının Önemi\n" +
                        "- Sessiz, karanlık ve serin bir ortam uyku kalitesini artırır. Gürültü ve ışık uykuya dalmayı zorlaştırır ve uykunun bölünmesine neden olur.\n" +
                        "- Oda sıcaklığının 16-20 °C arasında olması önerilir.\n" +
                        "- Rahat ve destekleyici yatak ve yastık seçimi, omurga sağlığı ve konfor açısından önemlidir.\n\n" +

                        "3. Uyku Alışkanlıkları ve Rutin\n" +
                        "- Her gün aynı saatlerde yatıp kalkmak, biyolojik saatin düzenlenmesine yardımcı olur.\n" +
                        "- Yatmadan önce telefon, tablet, televizyon gibi mavi ışık yayan cihazların kullanımını azaltmak melatonin hormonunun üretimini destekler.\n" +
                        "- Yatmadan önce ağır yemek, kafein ve alkol tüketimi uyku kalitesini düşürür.\n" +
                        "- Yatmadan önce rahatlama teknikleri (meditasyon, nefes egzersizi, hafif esneme) uykuya geçişi kolaylaştırır.\n\n" +

                        "4. Beslenmenin Uykuya Etkisi\n" +
                        "- Magnezyum, kalsiyum ve B vitamini açısından zengin besinler uyku kalitesini artırabilir.\n" +
                        "- Yatmadan önceki 2-3 saat içinde ağır ve yağlı yemeklerden kaçınılmalıdır.\n" +
                        "- Kafein ve şekerli içeceklerin tüketimi sınırlandırılmalıdır.\n\n" +

                        "5. Fiziksel Aktivitenin Rolü\n" +
                        "- Düzenli egzersiz, uyku kalitesini ve derin uyku süresini artırır.\n" +
                        "- Ancak, yatmaya çok yakın ağır egzersiz yapmak uykuya dalmayı zorlaştırabilir.\n\n" +

                        "6. Stres Yönetimi ve Uyku\n" +
                        "- Stres ve anksiyete, uyku problemlerinin en yaygın nedenlerindendir.\n" +
                        "- Gün içinde yapılan meditasyon ve nefes teknikleri stresi azaltarak uyku kalitesini iyileştirir.\n" +
                        "- Uyku öncesi yapılacak hafif yoga ve rahatlama egzersizleri zihni sakinleştirir.\n\n" +

                        "7. Uyku Bozuklukları\n" +
                        "- İnsomnia (uykusuzluk), uykuya dalma veya uykuyu sürdürmede zorluk yaşanmasıdır.\n" +
                        "- Uyku apnesi, solunum duraklamaları ile karakterizedir ve profesyonel müdahale gerektirir.\n" +
                        "- Horlama, uyku kalitesini düşürebilir ve sağlık sorunlarının belirtisi olabilir.\n\n" +

                        "8. Teknoloji ve Uyku\n" +
                        "- Mavi ışık filtresi uygulamaları veya gece modları kullanmak faydalıdır.\n" +
                        "- Elektronik cihazların yatak odasından uzak tutulması önerilir.\n\n" +

                        "9. Uyku Günlüğü Tutmanın Önemi\n" +
                        "- Uyku süresi, kalitesi ve rutini takip edilerek sorunlar daha iyi tespit edilir.\n" +
                        "- Hangi alışkanlıkların uykuya olumlu ya da olumsuz etkisi olduğu gözlemlenir.\n\n" +

                        "Sonuç olarak, uyku kalitesini artırmak için yaşam tarzında yapılacak küçük ama bilinçli değişiklikler büyük fark yaratır. Sağlıklı uyku, bedenin yenilenmesi, zihnin dinlenmesi ve genel yaşam kalitesinin yükselmesi için olmazsa olmazdır."
            ),

                    Article(
                "Su Tüketiminin Sağlık Üzerindeki Etkileri",
                R.drawable.susuzluk,
                "Su, yaşam için vazgeçilmez bir kaynaktır ve vücudumuzun yaklaşık %60'ını oluşturur. Her hücre, doku ve organın sağlıklı çalışması için yeterli su tüketimi şarttır.\n\n" +
                        "Fiziksel Sağlık Üzerindeki Etkileri\n" +
                        "- Su, vücut fonksiyonlarının düzenlenmesini sağlar, toksinlerin atılmasına yardımcı olur.\n" +
                        "- Yeterli su tüketilmediğinde baş ağrısı, halsizlik ve tansiyon sorunları görülebilir.\n" +
                        "- Böbreklerin sağlıklı çalışması ve sindirim sistemi için su şarttır.\n" +
                        "- Cilt sağlığını destekler, cildin nemli ve canlı kalmasına yardımcı olur.\n\n" +
                        "Zihinsel ve Duygusal Etkileri\n" +
                        "- Dehidrasyon hafif bile olsa odaklanma ve hafıza performansını düşürebilir.\n" +
                        "- Susuzluk stresi artırabilir, su içmek ruh halini iyileştirir.\n\n" +
                        "Spor Performansı ve Su Tüketimi\n" +
                        "- Egzersiz sırasında kaybedilen su, kas kramplarını ve yorgunluğu önlemek için yerine konmalıdır.\n" +
                        "- Kasların yenilenmesi ve performans için su gereklidir.\n\n" +
                        "Meditasyonda Su Tüketimi\n" +
                        "- Meditasyonla birlikte bilinçli su içme alışkanlığı sağlıklı yaşam için önemlidir.\n" +
                        "- Susamadan su içmek, beden-zihin dengesini destekler.\n\n" +
                        "Ne Kadar Su İçilmeli?\n" +
                        "- Günde 2-2.5 litre su genel öneridir.\n" +
                        "- Fiziksel aktivite, hava durumu ve yaşa göre ihtiyaç değişebilir.\n\n" +
                        "Sonuç olarak, su tüketimi fiziksel, zihinsel ve ruhsal sağlığımızın temel taşlarından biridir. Günlük yeterli ve düzenli su içmek uzun vadeli sağlıklı yaşamın anahtarıdır."
            ),

                    Article(
                    "Meditasyon Nedir & Faydaları",
            R.drawable.makale_1,
           "Meditasyon nedir?\n" +
                   "Meditasyon, Latince meditatio kelimesinden türemiştir ve derin düşünme anlamına gelir. Kişinin iç huzur, sükunet ve değişik bilinç halleri elde etmesini ve öz varlığına ulaşmasını sağlayan zihnini denetleme teknikleri ve deneyimleri olarak tanımlanır. Bir inanç türü veya dinsel bir ritüel değil; beden, ruh ve zihni şifalandırma yöntemi olarak kabul edilir. Meditasyon; taoizm, budizm, islamiyet gibi farklı inançlara sahip birçok farklı kültürde yer etmiştir ve bundan dolayı standart bir tekniği bulunmaz. Genellikle sessiz, sakin bir ortamda; bilinç bulanık durumda yapıldığı düşünülse de hareketli ve bilinç tamamen açık durumdayken yapılan meditasyon çeşitleri de vardır. Meditasyonda amaç; varlığı hissetmek, zihne odaklanmak ve zihni anlamak, sonucunda ise iç huzura ve dinginliğe ulaşmaktır.\n" +
                   "\n" +
                   "Meditasyon nasıl yapılır?\n" +
                   " Öncelikle meditasyona başlamadan dikkat edilmesi gereken bazı noktalar vardır. Bunlar şu şekildedir; \n" +
                   "\n" +
                   " - Huzurlu ve sakin bir ortam yaratmak: Özellikle meditasyona yeni başlayan kişilerde odaklanmayla ilgili sorunlar yaşanabilir. Bunun için telefon, tablet gibi cihazların titreşim ve sesini kapatmakla işe başlanmalıdır. Tekrarlayan sakin melodilerden oluşan bir müzik veya su sesi odaklanmaya yardımcı olabilir. Kulak tıkacı kullanıp tamamen sessiz bir ortam yaratmak yerine dış dünyadaki sesleri duymak ve zamanla rahatsız edici sesler olsa dahi zihne olan odağı bozmamayı öğrenmek daha yararlı olacaktır.\n" +
                   "\n" +
                   "- Rahat kıyafetler giymek: Hava durumuna göre giyinerek üşümenin veya terlemenin dikkati dağıtmasına engel olunmalıdır. Ayrıca kaşındırıcı, rahatsız edici kıyafetler de dikkati dağıtıp odaklanmayı zorlaştırabilir. Kıyafet değiştirmenin mümkün olmadığı bir alanda ise sadece ayakkabılar çıkarılarak odaklanmaya çalışılmalıdır.\n" +
                   "\n" +
                   "- Meditasyon süresini belirlemek: Genelde meditasyon için günde 2 kere 20 dakikalık bir süre önerilir ancak bu kişinin isteğine ve ihtiyacına göre değişebilir. Yeni başlayanlar için 20 dakika meditasyon yapmak zorlayıcı olacağı için 5 dakika ile başlayıp zamanla artırılabilir. Sürenin dolduğunu anlamak için sürekli saati kontrol etmek dikkat dağıtıcı olacağından hafif sesli bir alarm kurulmalıdır.\n" +
                   "\n" +
                   "- Vücudu esnetmek: Meditasyon, tek pozisyonda yapılacaksa sabit kalmak bir süre sonra rahatsız edici olabilir. Bunun önüne geçmek için meditasyon öncesinde esneme hareketleri yapılmalıdır. Oturma pozisyonu tercih edilecekse özellikle iç bacağı esnetmek gerekir. Ayrıca dik oturmak gerekeceğinden sırt kaslarının esnek ve rahat olması gerekir.\n" +
                   "\n" +
                   "-Rahat bir pozisyon bulmak: Meditasyon, pasif şekilde yapılacaksa uzun süreli kalmanın rahatsız etmeyeceği bir pozisyon bulmak önemlidir. Bunun için bir mindere bağdaş kurarak veya kurmadan, sandalyeye oturulacaksa da sandalyeyi ortalayarak tam oturma kemiklerinin üzerine hafif öne eğimli şekilde oturmak faydalı olacaktır. Ayrıca omurgayı dik tutmak ve bunu yaparken minimum efor sarf etmek de oldukça önemlidir.\n" +
                   "\n" +
                   "- Başlangıçta gözleri kapalı tutmak: İlk zamanlar gözlerin açık olması dikkati dağıtacağı için kapalı tutmak daha iyi olacaktır. Zamanla gözler açık durumda yapmaya başlanabilir ancak dikkat edilmesi gereken nokta gözler açıkken tek bir noktaya dalıp gitmek değil, bilinçli şekilde bütünü görüyor olmaktır. \n" +
                   "\n" +
                   "Tüm bu şartlar sağlandıktan sonra \"Meditasyon nasıl yapılır?\" sorusunun cevabına geçebiliriz.\n" +
                   "\n" +
                   "Zihni boşaltmak: Deneyimli kişiler zihnini tamamen boşaltabilse de yeni başlayanlar için bu pek mümkün değildir. İlk zamanlarda tek bir obje veya mantraya odaklanılmalı ve deneyim kazandıkça zihin tamamen boşaltılmaya çalışılmalıdır.\n" +
                   "\n" +
                   "Nefesi takip etmek: Nefes meditasyonu güzel bir başlangıç olabilir. Burada yapılması gereken nefesi yönetmeye çalışmadan nefes alıp verdiğinin farkında olmaktır. Bunu yapmak için göbek deliğinin üzerinde bir nokta belirleyip o noktanın nefes alıp verirken nasıl hareket ettiğine odaklanılmalıdır. Odaklanmayı kolaylaştırmak için o noktada düğme veya başka bir objenin olduğu düşünülebilir. Diyafram nefesi kullanmak önemlidir.\n" +
                   "\n" +
                   "Mantra tekrar etmek: Sankskritçede mantra, zihin enstrümanı anlamına gelir ve bir ses, kelime veya cümle olabilir. Mantra tekrarı yapmak zihni susturup derin bir evreye geçmek için güzel bir seçenektir. Geleneksel mantralardan olan \"Om\" sesi çıkarılabilir veya sessizlik, sakinlik, sükunet gibi kişiye huzur veren kelimeler tekrarlanabilir. Derin bir bilinç ve farkındalık evresine geçildiğinde mantrayı tekrar etmeye gerek kalmaz.\n" +
                   "\n" +
                   "Görsel objeye odaklanmak: Gözler açık şekilde meditasyon yapılıyorsa, bilinci derin evreye sokmak için mantra tekrarı yerine basit bir objeye odaklanmak da iyi bir seçenek olabilir. Burada dikkat edilmesi gereken baş ve boyun kasılmadan objeyi görebilir durumda olmaktır. Bunun için obje göz seviyesinde durmalıdır. Bir mumun alevi veya basit figürler, görsel obje olarak kullanılabilir. \n" +
                   "\n" +
                   "Zihinde canlandırmak: Derin farkındalık evresine geçmenin bir diğer yolu da zihinde canlandırma yöntemidir. Bu yöntemde amaç zihinde tamamen huzurlu ve sakin hissedilen bir mekan yaratmak ve derin evreye geçene kadar bu mekanı keşfetmektir. Bunun için rüzgarlı ama sıcak bir kumsal, yaprak hışırtıları ve kuş sesleri duyulan bir orman evi veya çiçeklerle dolu bir bahçe canlandırılabilir. Canlandırılan mekanda keşif yaparken tüm duyuları kullanmak; çevreyi ve objeleri görmek, koklamak, hissetmek önemlidir. Burada dikkat edilmesi gereken nokta canlandırılan mekanın tamamen gerçek bir yer olmamasıdır. Kişinin kendi hayal gücünü kullanması gerekir.\n" +
                   "\n" +
                   "Bedeni taramak: Bedeni taramak da derin evreye geçmek için iyi bir yoldur. Burada amaç bedenin tüm noktalarını keşfedip rahatlamak ve beden rahatlaması sonucu zihnin de rahatlama evresine girmesini sağlamaktır. Bunun için ayak parmaklarından başlayıp saç diplerine kadar sırayla bedenin her bir kasını tek tek hissetmek ve tüm kasları rahatlatmak gerekir.\n" +
                   "\n" +
                   "Yukarıdakiler gibi pasif yapılan meditasyon çeşitleri yanında tantra, kinhin, yoga ve taijiquan gibi aktif olarak yapılan meditasyonlar da vardır.\n" +
                   "\n" +
                   "Meditasyonun Faydaları\n" +
                   "\n" +
                   "Düzenli yapılan meditasyonun kişinin beyin dalgalarını etkilediği nörolojik görüntüleme yöntemleri sayesinde kanıtlanmıştır. Yapılan çalışmalarda meditasyonun yüksek kan basıncını düzenlediği, kalp atım hızını düşürdüğü ve bunların sonucunda kalp-damar hastalıklarında hastalık seyrini düzeltici etkisi olduğu; nörolojik incelemelerde beynin bilişsel-duyusal kısmını aktifleştirdiği; kasların gerilimini ve stres seviyesini azalttığı görülmüştür. Uzun yıllar çalışmaların sonucu kesin kanıt olarak görülmese de son yıllarda yapılan çalışmalarda meditasyonun stres düzeyini ve kan basıncını azalttığı kesin olarak kanıtlanmıştır. \n" +
                   "\n" +
                   " Stres seviyesinin azalmasının faydaları şunlardır;\n" +
                   "\n" +
                   "- Daha hızlı ve doğru kararlar almayı sağlar.\n" +
                   "\n" +
                   "- Mutluluk seviyesi yükselir.\n" +
                   "\n" +
                   "- Öğrenme ve hafıza gelişir.\n" +
                   "\n" +
                   "- Odaklanma süresi ve kalitesi artar.\n" +
                   "\n" +
                   "- Problem çözme yeteneği gelişir.\n" +
                   "\n" +
                   "- Beyin kapasitesi artar.\n" +
                   "\n" +
                   "- Hem psikolojik hem de fiziksel olarak iyi hissetmeyi sağlar.\n" +
                   "\n" +
                   "- Yaratıcılık seviyesi artar.\n" +
                   "\n" +
                   "- Stresle ilişkili yaşlanma azalır.\n" +
                   "\n" +
                   "- Kaygıların üstesinden gelmek kolaylaşır.\n" +
                   "\n" +
                   "- Değişimlere daha kolay adapte olmayı sağlar.\n" +
                   "\n" +
                   "- Otokontrolün gelişmesini sağlar.\n" +
                   "\n" +
                   "- Kişinin kendine odaklanmasını ve kendini keşfetmesini sağlar.\n" +
                   "\n" +
                   "- Kişisel farkındalık artar.\n" +
                   "\n" +
                   "- Sosyal ilişkilerin daha güçlü olmasını sağlar.\n" +
                   "\n" +
                   "- İş ve okul hayatında başarının artmasını sağlar.\n" +
                   "\n" +
                   "Özetle meditasyon, kişinin iç huzuru ve farkındalığı bulmasını sağlayarak stres seviyesini düşürüp daha kaliteli bir yaşam sürmesini sağlar.\n" +
                   "\n"
        )
        )

        val adapter = ArticleAdapter(articleList)
        recyclerView.adapter = adapter

        tvSaglik.setOnClickListener {
            var intent = Intent(this@AnaMainActivity, SaglikActivity::class.java)
            startActivity(intent)
        }
        tvSpor.setOnClickListener {
            var intent = Intent(this@AnaMainActivity, SporDetayActivity::class.java)
            startActivity(intent)
        }
        tvVucut.setOnClickListener {
            var intent=Intent(this, VucutActivity::class.java)
            startActivity(intent)
        }
        tvGorev.setOnClickListener {
            var intent=Intent(this, GorevOlusturActivity::class.java)
            startActivity(intent)
        }


        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)

        val greeting = when (hour) {
            in 5..11 -> "Günaydın,Öykü★"
            in 12..17 -> "İyi Günler, Öykü★"
            in 18..22 -> "İyi Akşamlar, Öykü★"
            else -> "İyi Geceler, Öykü★"
        }

        tvGun.text = greeting


        bottomnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // Ana sayfa zaten burası, ekstra işlem yok
                    true
                }

                R.id.nav_repice -> {
                    val intent = Intent(this, TarifListesiActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_ayarlar -> {
                    val intent = Intent(this, ProfilActivity::class.java)
                    startActivity(intent)
                    true
                }




                else -> false
            }
        }

        }
    }





