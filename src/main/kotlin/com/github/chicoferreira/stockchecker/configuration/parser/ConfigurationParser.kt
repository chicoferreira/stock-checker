package com.github.chicoferreira.stockchecker.configuration.parser

import com.github.chicoferreira.stockchecker.product.Product

interface ConfigurationParser {

    fun fromJson(jsonString: String): List<Product>

    fun toJson(productList: List<Product>): String

}