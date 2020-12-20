package com.github.chicoferreira.stockchecker.product

import com.github.chicoferreira.stockchecker.StockCheckResult
import com.github.chicoferreira.stockchecker.configuration.parser.KtxSerializationConfigurationParser
import com.github.chicoferreira.stockchecker.parser.Website
import kotlinx.serialization.Serializable

@Serializable(with = KtxSerializationConfigurationParser.ProductSerializer::class)
data class Product(val url: String) {

  lateinit var website: Website
  var latestResult: StockCheckResult? = null

  fun isLoaded(): Boolean = this::website.isInitialized

}