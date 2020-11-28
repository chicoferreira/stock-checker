package com.github.chicoferreira.stockchecker.parser

import com.github.chicoferreira.stockchecker.StockCheckResult
import org.jsoup.nodes.Document

class PCDIGAWebsiteParser : WebsiteParser {
    override fun parse(document: Document): StockCheckResult {
        val productName = document.select("meta[property=og:title]")
                .first()
                .attr("content")

        val price = document.select("meta[property=product:price:amount]")
                .first()
                .attr("content")
                .toDouble()

        val priceCurrency = document.select("meta[property=product:price:currency]")
                .first()
                .attr("content")

        val stockColumn = document.select(".col-lg-3.bg-white")

        val available = stockColumn.select(".product.alert.stock").isEmpty()
                        && stockColumn.select(".product-add-form").isNotEmpty()

        val availableShops = LinkedHashMap<String, Boolean>()

        for (element in stockColumn.select(".store-stock-location")) {
            val availableShop = element.select(".icon-checkmark").isNotEmpty()

            availableShops[element.text()] = availableShop
        }

        return StockCheckResult(productName, price, priceCurrency, available, availableShops)
    }
}