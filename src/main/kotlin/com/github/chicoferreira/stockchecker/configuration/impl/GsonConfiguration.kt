package com.github.chicoferreira.stockchecker.configuration.impl

import com.github.chicoferreira.stockchecker.configuration.adapter.ProductTypeAdapter
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.util.FileManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Reader


class GsonConfiguration(val logger: Logger, val fileManager: FileManager) {

    val gson: Gson = GsonBuilder().registerTypeAdapter(Product::class.java, ProductTypeAdapter()).create()
    val fileName = ".stock-checker-data.json"

    fun load(): List<Product> {
        val result = mutableListOf<Product>()

        fileManager.readFile(fileName) {
            parse(it)?.also { products -> result.addAll(products) }
        }

        return result
    }

    private fun parse(reader: Reader): List<Product>? {
        val type = object : TypeToken<List<Product>>() {}.type

        return gson.fromJson<List<Product>>(reader, type)
    }

    fun save(productList: List<Product>) = fileManager.writeFile(fileName) {
        gson.toJson(productList, it)
    }

}