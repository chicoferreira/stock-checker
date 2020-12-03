package com.github.chicoferreira.stockchecker.parser.property

import com.github.chicoferreira.stockchecker.logger.ConsoleColor.*
import com.github.chicoferreira.stockchecker.util.Price

class ProductProperties {

    companion object {

        val PRODUCT_NAME = buildProperty<String>("product_name") { "$DARK_GRAY$it" }

        val PRICE = buildProperty<Price>("price") {
            "${RESET}for ${WHITE}${it.value.toString()} ${it.currency}"
        }

        val AVAILABILITY = buildProperty<Boolean>("availability") {
            if (it) "${GREEN}IN STOCK" else "${RED}NOT IN STOCK"
        }

        val SHOP_AVAILABILITY = buildProperty<Map<String, Boolean>>("shop_availability") {
            val availableCount = it.count { result -> result.value }
            val shopCount = it.count()

            val color = when (availableCount) {
                shopCount -> DARK_GREEN
                0 -> RED
                else -> YELLOW
            }

            "${RESET}available in $color" +
            "${availableCount}/${shopCount}$RESET shops"
        }

        private inline fun <reified T> buildProperty(
                name: String, crossinline render: (T) -> String,
        ): ProductProperty<T> {
            return object : ProductProperty<T> {
                override val name: String = name

                override fun render(value: T): String {
                    return render(value)
                }
            }
        }

    }

}

