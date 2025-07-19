package com.example.fitnest

import java.io.Serializable


data class Tarif(
    val baslik: String,
    val aciklama: String,
    val malzemeler: List<String>,
    val yapilis: List<String>,
    val kalori: Int,
    val resimUrl: String
): Serializable
