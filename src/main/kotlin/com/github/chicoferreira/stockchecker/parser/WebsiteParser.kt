package com.github.chicoferreira.stockchecker.parser

import com.github.chicoferreira.stockchecker.StockCheckResult
import org.jsoup.nodes.Document

interface WebsiteParser {

    fun parse(document: Document): StockCheckResult

}