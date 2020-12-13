package com.github.chicoferreira.stockchecker.product

import com.github.chicoferreira.stockchecker.StockCheckResult
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.parser.Website
import org.jsoup.Jsoup

class ProductController(private val logger: Logger) {

    fun load(product: Product): Boolean {
        val url = product.url
        val website = Website.values().find { it.isUrl(url) }

        if (website == null) {
            logger.warning("Couldn't find parser for $url.")
            return false
        }

        logger.info("Found ${website.name} parser for $url.")

        product.website = website
        return true
    }

    fun connect(product: Product): StockCheckResult {
        return try {
            val document = Jsoup.connect(product.url).get()
            product.website.parser.parse(document).also {
                product.latestResult = it
            }
        } catch (exception: Exception) {
            logger.warning("Couldn't fetch info from ${product.url}:")
            throw exception
        }
    }

    private fun Website.isUrl(url: String): Boolean {
        val cleanUrl = url
                .replaceFirst("https://www.", "")
                .replaceFirst("http://www.", "")
        return cleanUrl.startsWith(websiteUrl)
    }

}