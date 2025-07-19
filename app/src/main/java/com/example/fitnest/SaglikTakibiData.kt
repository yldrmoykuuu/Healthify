package com.example.fitnest

data class SaglikTakibiData(
    val su: Map<String, Int>? = null,    // tarih - ml cinsinden su
    val uyku: Map<String, Double>? = null // tarih - saat cinsinden uyku
)
