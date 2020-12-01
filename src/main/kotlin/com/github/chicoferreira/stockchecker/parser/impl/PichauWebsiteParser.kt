package com.github.chicoferreira.stockchecker.parser.impl

import com.github.chicoferreira.stockchecker.StockCheckResult
import com.github.chicoferreira.stockchecker.parser.WebsiteParser
import org.jsoup.nodes.Document

/**
 * @author Henry Fábio
 */
class PichauWebsiteParser : WebsiteParser {

    override fun parse(document: Document): StockCheckResult {
        val productName = document.selectFirst("meta[property=og:title]")
                .attr("content")

        val productPrice = document.selectFirst("meta[property=product:price:amount]")
                .attr("content")
                .toDouble()

        val priceCurrency = document.selectFirst("meta[property=product:price:currency]")
                .attr("content")

        val available = document.select("div[class=stock available]").isNotEmpty()

        return StockCheckResult(productName, productPrice, priceCurrency, mapOf("main" to available))
    }

}