package com.github.chicoferreira.stockchecker

data class StockCheckResult(
    val productName: String,
    val price: Double,
    val priceCurrency: String,
    val available: Boolean,
    val availableShops: Map<String, Boolean>
)
