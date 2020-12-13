package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.product.ProductController
import com.github.chicoferreira.stockchecker.product.ProductManager
import java.util.*
import kotlin.collections.LinkedHashMap

class StockCheckerTask(
    val productManager: ProductManager,
    val productController: ProductController,
    val logger: Logger,
) : (TimerTask) -> Unit {

    val lastAccessMap = LinkedHashMap<Product, Long>()
    val lastAccessWebsiteMap = LinkedHashMap<Website, Long>()

    override fun invoke(p1: TimerTask) {
        val product: Product = findNextProduct() ?: return

        productController.connect(product).also { logger.info(it.buildRender()) }

        System.currentTimeMillis().also {
            lastAccessWebsiteMap[product.website] = it
            lastAccessMap[product] = it
        }
    }

    fun findNextProduct(): Product? {
        var product: Product? = null

        for (currentProduct in productManager.getAll()) {
            if (!currentProduct.website.isInDelay()) {
                if (product == null || currentProduct.lastAccessed < product.lastAccessed) {
                    product = currentProduct
                }
            }
        }

        return product
    }

    private val Product.lastAccessed: Long
        get() = lastAccessMap[this] ?: 0.toLong()

    private fun Website.isInDelay(): Boolean {
        val lastTimeAccessed = lastAccessWebsiteMap[this] ?: return false

        return System.currentTimeMillis() - lastTimeAccessed <= this.delayInSeconds * 1000
    }

}