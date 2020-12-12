package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.console.Console
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.product.ProductManager
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*

class StockCheckerTask(val productManager: ProductManager, val console: Console) : (TimerTask) -> Unit {

    // TODO: website intelligent based delay
    var currentProductIndex: Int = -1

    override fun invoke(p1: TimerTask) {
        currentProductIndex = if (currentProductIndex + 1 >= productManager.size()) 0 else currentProductIndex + 1

        if (productManager.empty()) return

        val product = productManager[currentProductIndex]
        connect(product)
    }

    private fun connect(product: Product) {
        runCatching<Document> {
            Jsoup.connect(product.url).get()
        }.onSuccess {
            product.website.parser.parse(it).buildRender().also { result -> console.info(result) }
        }.onFailure {
            console.warning("Couldn't fetch info from ${product.url}: ${it.message}")
        }
    }
}