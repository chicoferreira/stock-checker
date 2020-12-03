package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.parser.property.ProductProperty

data class StockCheckResult(val list: List<ProductProperty.Value<*>>) {

    constructor(vararg values: ProductProperty.Value<*>) : this(values.toList())

    fun buildRender(): String {
        val builder = StringBuilder()

        for (property: ProductProperty.Value<*> in list) builder.append(property.render()).append(" ")

        return builder.toString()
    }
}