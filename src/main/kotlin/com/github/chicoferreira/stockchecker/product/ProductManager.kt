package com.github.chicoferreira.stockchecker.product

import java.util.*

class ProductManager() {

    private val productList: MutableList<Product>

    init {
        productList = LinkedList<Product>()
    }

    fun getAll(): List<Product> = productList

    fun register(product: Product) {
        if (!product.isLoaded()) {
            throw ProductNotLoadedException()
        }

        productList.add(product)
    }

    fun remove(index: Int): Product = productList.removeAt(index)

    fun empty() = productList.isEmpty()

    fun size() = productList.size

    operator fun get(index: Int) = productList[index]

    operator fun plusAssign(product: Product) = register(product)

    class ProductNotLoadedException() : IllegalStateException("Product is not loaded")

}