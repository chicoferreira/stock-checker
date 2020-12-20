package com.github.chicoferreira.stockchecker.configuration.parser

import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.product.Product
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GsonConfigurationParser : ConfigurationParser {

    val gson: Gson = GsonBuilder().registerTypeAdapter(Product::class.java, ProductTypeAdapter()).create()

    override fun fromJson(jsonString: String): List<Product> {
        val type = object : TypeToken<List<Product>>() {}.type

        return gson.fromJson<List<Product>>(jsonString, type) ?: emptyList()
    }

    override fun toJson(productList: List<Product>): String {
        return gson.toJson(productList)
    }

    class ProductTypeAdapter : JsonSerializer<Product>, JsonDeserializer<Product> {
        override fun serialize(src: Product?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonObject().also {
                it.addProperty("url", src?.url)
                it.add("website", context?.serialize(src?.website))
            }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Product {
            val jsonObject = json?.asJsonObject
            val url = jsonObject?.get("url")?.asString
            val website = context?.deserialize<Website>(jsonObject?.get("website"), Website::class.java)
            return Product(url!!).also {
                it.website = website!!
            }
        }
    }
}