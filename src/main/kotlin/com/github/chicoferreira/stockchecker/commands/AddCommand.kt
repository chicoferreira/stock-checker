package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.command.Command
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.product.ProductLoader
import com.github.chicoferreira.stockchecker.product.ProductManager

class AddCommand(val logger: Logger, val productManager: ProductManager, val productLoader: ProductLoader) : Command {
    override val name: String = "add"

    override fun execute(args: List<String>) {
        if (args.isEmpty()) {
            logger.warning("Wrong syntax. Use 'add <link>'.")
            return
        }

        val link = args[0]

        val product = Product(link)

        if (productLoader.loadProduct(product)) {
            productManager += product
        }
    }
}