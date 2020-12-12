package com.github.chicoferreira.stockchecker.product

import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.parser.Website

class ProductLoader(private val logger: Logger) {

    fun loadProduct(product: Product): Boolean {
        val url = product.url

        logger.info("Connecting to $url...")

        val website = Website.values().find { it.isUrl(url) }

        if (website == null) {
            logger.warning("Couldn't find parser for $url.")
            return false
        }

        logger.info("Found ${website.name} parser for $url.")

        product.website = website

        return true
    }

    private fun Website.isUrl(url: String): Boolean {
        val cleanUrl = url
                .replaceFirst("https://www.", "")
                .replaceFirst("http://www.", "")
        return cleanUrl.startsWith(websiteUrl)
    }

}