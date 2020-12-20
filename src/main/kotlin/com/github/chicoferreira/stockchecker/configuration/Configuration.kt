package com.github.chicoferreira.stockchecker.configuration

import com.github.chicoferreira.stockchecker.configuration.parser.ConfigurationParser
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.util.FileManager
import java.util.stream.Collectors

class Configuration(val configurationParser: ConfigurationParser) {

    val fileName = ".stock-checker-data.json"
    val fileManager = FileManager()

    fun loadProducts(): List<Product> {
        val jsonString = fileManager.readFile(fileName) {
            it.lines().collect(Collectors.joining())
        }

        return configurationParser.fromJson(jsonString)
    }

    fun saveProducts(productList: List<Product>) {
        val jsonString = configurationParser.toJson(productList)

        fileManager.writeFile(fileName) {
            it.write(jsonString)
        }
    }
}