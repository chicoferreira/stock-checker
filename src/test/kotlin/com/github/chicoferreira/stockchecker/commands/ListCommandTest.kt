package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.ProductManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ListCommandTest {

    val logger = mockk<Logger>(relaxed = true, relaxUnitFun = true)
    val productManager = mockk<ProductManager>(relaxed = true, relaxUnitFun = true)
    val listCommand = ListCommand(logger, productManager)

    @Test
    fun `test list command`() {
        every { productManager.empty() } returns false

        listCommand.execute(emptyList())

        verify {
            productManager.getAll()
        }
    }
}