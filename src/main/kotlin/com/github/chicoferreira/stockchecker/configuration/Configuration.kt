package com.github.chicoferreira.stockchecker.configuration

import com.github.chicoferreira.stockchecker.configuration.adapter.ProductTypeAdapter
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.Product
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileWriter
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Paths


class Configuration(val logger: Logger) {

    val gson: Gson
    val fileName = "data.json"

    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Product::class.java, ProductTypeAdapter())
        gson = gsonBuilder.create()
    }

    private fun read(): BufferedReader? {
        val path = Paths.get(fileName)
        val file = path.toFile()

        if (!file.exists() && !file.createNewFile()) {
            logger.warning("Couldn't create configuration file.")
            return null
        }

        return Files.newBufferedReader(path)
    }

    fun load(): List<Product> {
        val result = mutableListOf<Product>()
        val reader = read() ?: return result

        parse(reader)?.also {
            result.addAll(it)
        }

        reader.close()

        return result
    }

    private fun parse(reader: Reader): List<Product>? {
        val type = object : TypeToken<List<Product>>() {}.type

        return gson.fromJson<List<Product>>(reader, type).also {
            reader.close()
        }
    }

    fun save(productList: List<Product>) {
        FileWriter(fileName).use { writer ->
            gson.toJson(productList, writer)
        }
    }

}