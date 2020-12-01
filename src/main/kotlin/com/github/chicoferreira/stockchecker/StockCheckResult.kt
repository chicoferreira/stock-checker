package com.github.chicoferreira.stockchecker

data class StockCheckResult(
    val productName: String,
    val price: Double,
    val priceCurrency: String,
    val availableShops: Map<String, Boolean>
) {

    fun isAvailable(): Boolean {
        return availableShops.any { it.value }
    }

}
