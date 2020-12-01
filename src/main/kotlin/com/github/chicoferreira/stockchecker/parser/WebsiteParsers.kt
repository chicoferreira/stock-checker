package com.github.chicoferreira.stockchecker.parser

import com.github.chicoferreira.stockchecker.parser.impl.PCDIGAWebsiteParser
import com.github.chicoferreira.stockchecker.parser.impl.PichauWebsiteParser

enum class WebsiteParsers(val websiteUrl: String, val parser: WebsiteParser) {

    PCDIGA("pcdiga.com", PCDIGAWebsiteParser()),
    PICHAU("pichau.com.br", PichauWebsiteParser());

    fun isUrl(url: String): Boolean {
        val cleanUrl = url
                .replaceFirst("https://www.", "")
                .replaceFirst("http://www.", "")
        return cleanUrl.startsWith(websiteUrl)
    }

}