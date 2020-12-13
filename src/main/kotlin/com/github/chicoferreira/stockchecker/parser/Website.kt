package com.github.chicoferreira.stockchecker.parser

import com.github.chicoferreira.stockchecker.parser.impl.PCDIGAWebsiteParser
import com.github.chicoferreira.stockchecker.parser.impl.PichauWebsiteParser

enum class Website(val websiteUrl: String, val parser: WebsiteParser, val delayInSeconds: Long = 5) {

    PCDIGA("pcdiga.com", PCDIGAWebsiteParser()),
    PICHAU("pichau.com.br", PichauWebsiteParser(), 10);

}