package com.github.chicoferreira.stockchecker.configuration.parser

import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.product.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal abstract class ConfigurationParserTest {

    abstract val parser: ConfigurationParser

    val productJsonToTest = """[
          {
            "url": "pcdiga.com/product",
            "website": "PCDIGA"
          },
          {
            "url": "pichau.com.br/product",
            "website": "PICHAU"
          }
        ]""".replace("\\s".toRegex(), "")

    val productListToTest = listOf(Product("pcdiga.com/product").also { it.website = Website.PCDIGA },
                                   Product("pichau.com.br/product").also { it.website = Website.PICHAU })

    @Test
    fun testFromJson() {
        assertEquals(productListToTest, parser.fromJson(productJsonToTest))
    }

    @Test
    fun testToJson() {
        assertEquals(productJsonToTest, parser.toJson(productListToTest))
    }
}