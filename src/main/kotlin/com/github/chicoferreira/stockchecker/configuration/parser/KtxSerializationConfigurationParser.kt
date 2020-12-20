package com.github.chicoferreira.stockchecker.configuration.parser

import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.product.Product
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

private val json = Json {}

class KtxSerializationConfigurationParser : ConfigurationParser {
  override fun fromJson(jsonString: String): List<Product> {
    return json.decodeFromString(ListSerializer(ProductSerializer), jsonString)
  }

  override fun toJson(productList: List<Product>): String {
    return json.encodeToString(ListSerializer(ProductSerializer), productList)
  }

  internal object ProductSerializer : KSerializer<Product> {
    @Serializable
    private data class ProductSurrogate(val url: String, val website: String)

    override val descriptor: SerialDescriptor = ProductSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Product) {
      val surrogate = ProductSurrogate(url = value.url, website = value.website.toString())

      encoder.encodeSerializableValue(ProductSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): Product {
      val surrogate = decoder.decodeSerializableValue(ProductSurrogate.serializer())

      return Product(surrogate.url).apply {
        website = Website.valueOf(surrogate.website)
      }
    }
  }
}