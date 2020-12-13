package com.github.chicoferreira.stockchecker.product.property

interface ProductProperty<T> {

    val name: String

    fun render(value: T): String

    fun ofValue(value: T): Value<T> = Value<T>(this, value)

    data class Value<T>(val property: ProductProperty<T>, val value: T) {

        fun render(): String {
            return property.render(value)
        }


    }

}

