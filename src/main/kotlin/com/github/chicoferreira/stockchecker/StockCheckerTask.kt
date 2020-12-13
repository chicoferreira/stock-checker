package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.ProductController
import com.github.chicoferreira.stockchecker.product.ProductManager
import java.util.*

class StockCheckerTask(
    val productManager: ProductManager,
    val productController: ProductController,
    val logger: Logger
) : (TimerTask) -> Unit {

    // TODO: website intelligent based delay
    var currentProductIndex: Int = -1

    override fun invoke(p1: TimerTask) {
        currentProductIndex = if (currentProductIndex + 1 >= productManager.size()) 0 else currentProductIndex + 1

        if (productManager.empty()) return

        val product = productManager[currentProductIndex]
        productController.connect(product).also { logger.info(it.buildRender()) }
    }

}