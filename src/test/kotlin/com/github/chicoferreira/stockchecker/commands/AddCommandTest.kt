package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.product.ProductController
import com.github.chicoferreira.stockchecker.product.ProductManager
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class AddCommandTest {

    val logger = mockk<Logger>(relaxed = true, relaxUnitFun = true)
    val productManager = mockk<ProductManager>(relaxed = true, relaxUnitFun = true)
    val productController = mockk<ProductController>(relaxed = true, relaxUnitFun = true)
    val addCommand = AddCommand(logger, productManager, productController)

    @Test
    fun `test add command`() {
        every { productController.load(any()) } returns true

        addCommand.execute(listOf(Website.PCDIGA.websiteUrl))

        verify {
            productManager += any()
        }
    }
}