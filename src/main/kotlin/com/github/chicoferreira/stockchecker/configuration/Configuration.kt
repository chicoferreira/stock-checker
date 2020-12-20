package com.github.chicoferreira.stockchecker.configuration

import com.github.chicoferreira.stockchecker.configuration.parser.ConfigurationParser
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.util.FileManager
import java.nio.file.Path

class Configuration(
    val configurationParser: ConfigurationParser,
    val fileManager: FileManager,
    val saveDirectory: Path,
    val fileName: String,
) {

    fun loadProducts(): List<Product> {
        val file = fileManager.getOrCreateFile(fileName, saveDirectory)
        val jsonString = fileManager.readFromFile(file)

        return configurationParser.fromJson(jsonString)
    }

    fun saveProducts(productList: List<Product>) {
        val file = fileManager.getOrCreateFile(fileName, saveDirectory)
        val jsonString = configurationParser.toJson(productList)

        fileManager.saveToFile(file, jsonString)
    }
}