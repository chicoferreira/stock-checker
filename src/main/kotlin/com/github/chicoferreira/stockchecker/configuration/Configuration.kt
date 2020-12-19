package com.github.chicoferreira.stockchecker.configuration

import com.github.chicoferreira.stockchecker.product.Product
import java.io.Reader

interface Configuration {

    fun load(reader: Reader): List<Product>

    fun save(products: List<Product>)

}