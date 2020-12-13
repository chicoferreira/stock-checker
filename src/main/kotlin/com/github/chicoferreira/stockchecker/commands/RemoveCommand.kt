package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.command.Command
import com.github.chicoferreira.stockchecker.console.ConsoleColor
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.ProductManager
import com.github.chicoferreira.stockchecker.product.property.ProductProperties

class RemoveCommand(val logger: Logger, val productManager: ProductManager) : Command {
    override val name: String = "remove"

    override fun execute(args: List<String>) {
        if (args.isEmpty()) {
            logger.warning("Wrong syntax. Use 'remove <index>'.")
            return
        }

        val index = args[0].toIntOrNull()
        if (index == null) {
            logger.warning("Couldn't parse index. Use 'remove <index>'.")
            return
        }

        val size = productManager.size()
        if (index < 0 || index >= size) {
            logger.warning("Index out of bounds. Should be between [0, ${size - 1}].")
            return
        }

        val remove = productManager.remove(index)

        val productName = remove.latestResult?.get(ProductProperties.PRODUCT_NAME) ?: "UNKNOWN (${index}) "

        logger.info("Removed $productName ${ConsoleColor.RESET}product.")
    }
}