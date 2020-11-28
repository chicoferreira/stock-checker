package com.github.chicoferreira.stockchecker.parser

enum class WebsiteParsers(val websiteUrl: String, val parser: WebsiteParser) {

    PCDIGA("https://www.pcdiga.com/", PCDIGAWebsiteParser())

}