package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.command.Command
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.Product
import com.github.chicoferreira.stockchecker.product.ProductManager
import com.github.chicoferreira.stockchecker.product.property.ProductProperties

class ListCommand(val logger: Logger, val productManager: ProductManager) : Command {
    override val name: String = "list"

    override fun execute(args: List<String>) {
        logger.info("Product list:")
        for ((index, product) in productManager.getAll().withIndex()) {
            logger.info("- ($index) ${findDisplayName(product)}")
        }
        logger.info()
    }

    private fun findDisplayName(product: Product): String {
        val latestResult = product.latestResult

        if (latestResult != null) {
            val stringBuilder = StringBuilder()

            val nameValue = latestResult[ProductProperties.PRODUCT_NAME]
            if (nameValue != null) {
                stringBuilder.append(nameValue.render()).append(" ")
            }

            val availabilityValue = latestResult[ProductProperties.AVAILABILITY]
            if (availabilityValue != null) {
                stringBuilder.append(availabilityValue.render())
            }

            if (stringBuilder.isNotEmpty()) {
                return "[${product.website.name}] ${stringBuilder.toString()}"
            }
        }

        return "[${product.website.name}] UNKNOWN (${product.url})"
    }

}