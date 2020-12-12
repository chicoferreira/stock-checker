package com.github.chicoferreira.stockchecker.product

import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.parser.WebsiteParser

data class Product(val url: String) {

    lateinit var website: Website

    fun isLoaded(): Boolean = this::website.isInitialized

}