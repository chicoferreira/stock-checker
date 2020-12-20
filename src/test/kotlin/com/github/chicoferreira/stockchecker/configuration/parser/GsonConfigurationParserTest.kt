package com.github.chicoferreira.stockchecker.configuration.parser

internal class GsonConfigurationParserTest : ConfigurationParserTest() {
    override val parser: ConfigurationParser = GsonConfigurationParser()
}