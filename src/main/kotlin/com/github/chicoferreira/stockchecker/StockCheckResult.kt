package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.product.property.ProductProperty
import com.github.chicoferreira.stockchecker.product.property.ProductProperty.Value

data class StockCheckResult(val list: List<Value<*>>) {

    constructor(vararg values: Value<*>) : this(values.toList())

    fun buildRender(): String {
        val builder = StringBuilder()

        for (property: Value<*> in list) builder.append(property.render()).append(" ")

        return builder.toString()
    }

    @Suppress("UNCHECKED_CAST")
    inline operator fun <reified T> get(name: String): Value<T>? {
        return list
            .firstOrNull { it.property.name.equals(name, ignoreCase = true) && it.value is T }
            ?.let { it as Value<T> }
    }

    inline operator fun <reified T> get(property: ProductProperty<T>): Value<T>? = get<T>(property.name)
}