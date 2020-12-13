package com.github.chicoferreira.stockchecker.parser.impl

import com.github.chicoferreira.stockchecker.StockCheckResult
import com.github.chicoferreira.stockchecker.parser.WebsiteParser
import com.github.chicoferreira.stockchecker.product.property.ProductProperties
import com.github.chicoferreira.stockchecker.util.Price
import org.jsoup.nodes.Document

class PCDIGAWebsiteParser : WebsiteParser {
    override fun parse(document: Document): StockCheckResult {
        val productName = document.select("meta[property=og:title]").first().attr("content")

        val price = document.select("meta[property=product:price:amount]").first().attr("content").toBigDecimal()

        val priceCurrency = document.select("meta[property=product:price:currency]").first().attr("content")

        val stockColumn = document.select(".col-lg-3.bg-white")

        val available = stockColumn.select(".product.alert.stock").isEmpty()
                        && stockColumn.select(".product-add-form").isNotEmpty()

        val availableShops = LinkedHashMap<String, Boolean>()

        for (element in stockColumn.select(".store-stock-location")) {
            val availableShop = element.select(".icon-checkmark").isNotEmpty()

            availableShops[element.text()] = availableShop
        }

        val properties = mutableListOf(ProductProperties.PRODUCT_NAME.ofValue(productName),
                                       ProductProperties.AVAILABILITY.ofValue(available),
                                       ProductProperties.PRICE.ofValue(Price(price, priceCurrency)))

        if (available) properties += ProductProperties.SHOP_AVAILABILITY.ofValue(availableShops)

        return StockCheckResult(properties)
    }
}