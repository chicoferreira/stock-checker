package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.product.ProductManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class RemoveCommandTest {

    val logger = mockk<Logger>(relaxed = true, relaxUnitFun = true)
    val productManager = mockk<ProductManager>(relaxed = true, relaxUnitFun = true)
    val removeCommand = RemoveCommand(logger, productManager)

    @Test
    fun `test remove command`() {
        every { productManager.size() } returns 1

        removeCommand.execute(listOf("0"))

        verify {
            productManager.remove(0)
        }
    }
}