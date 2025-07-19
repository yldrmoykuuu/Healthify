package com.example.fitnest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TarifAdapter(private val tarifList: List<Tarif>, val onClick: (Tarif) -> Unit) :
    RecyclerView.Adapter<com.example.fitnest.TarifAdapter.TarifViewHolder>() {

    inner class TarifViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarif_card, parent, false)
        return TarifViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarifViewHolder, position: Int) {
        val tarif = tarifList[position]
        holder.view.findViewById<TextView>(R.id.txtBaslik).text = tarif.baslik
        holder.view.findViewById<TextView>(R.id.txtAciklama).text = tarif.aciklama
        // Glide veya Picasso ile resim yükleme
        Glide.with(holder.view.context).load(tarif.resimUrl).into(holder.view.findViewById(R.id.imgTarif))


        holder.view.setOnClickListener {
            val context = holder.view.context
            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)

            // AlertDialog için özel layout tasarımı (fragment_tarif_detay.xml ile aynı veya benzer olabilir)
            val dialogView = inflater.inflate(R.layout.activity_tarif_detay, null)

            // View bileşenlerini bağla
            val imgTarifDetay = dialogView.findViewById<ImageView>(R.id.imgTarifDetay)
            val txtBaslik = dialogView.findViewById<TextView>(R.id.txtBaslik)
            val txtKalori = dialogView.findViewById<TextView>(R.id.txtKalori)
            val txtMalzemeler = dialogView.findViewById<TextView>(R.id.txtMalzemeler)
            val txtYapilis = dialogView.findViewById<TextView>(R.id.txtYapilis)

            // Verileri doldur
            txtBaslik.text = tarif.baslik
            txtKalori.text = "${tarif.kalori} kalori"
            txtMalzemeler.text = tarif.malzemeler.joinToString(separator = "\n• ", prefix = "• ")
            txtYapilis.text = tarif.yapilis.mapIndexed { i, adim -> "${i + 1}. $adim" }.joinToString("\n")

            Glide.with(context).load(tarif.resimUrl).into(imgTarifDetay)

            builder.setView(dialogView)
                .setPositiveButton("Kapat") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }


    override fun getItemCount() = tarifList.size
}

