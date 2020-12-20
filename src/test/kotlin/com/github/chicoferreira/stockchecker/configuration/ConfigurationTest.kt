package com.github.chicoferreira.stockchecker.configuration

import com.github.chicoferreira.stockchecker.configuration.parser.ConfigurationParser
import com.github.chicoferreira.stockchecker.util.FileManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.nio.file.Path

internal class ConfigurationTest {

    val configurationParser = mockk<ConfigurationParser>(relaxed = true)
    val fileManager = mockk<FileManager>(relaxed = true, relaxUnitFun = true)
    val path = mockk<Path>()
    val fileName = "test-file-name"

    val configuration = Configuration(configurationParser, fileManager, path, fileName)

    @Test
    fun testLoadProducts() {
        configuration.loadProducts()

        verify {
            configurationParser.fromJson(any())
        }
    }

    @Test
    fun testSaveProducts() {
        configuration.saveProducts(emptyList())

        verify {
            fileManager.saveToFile(any(), any())
        }
    }

}