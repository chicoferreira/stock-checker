package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.StockChecker
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.parser.Website
import com.github.chicoferreira.stockchecker.product.ProductController
import com.github.chicoferreira.stockchecker.product.ProductManager
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CommandsTest {

    val logger = mockk<Logger>(relaxed = true, relaxUnitFun = true)
    val productController = mockk<ProductController>(relaxed = true, relaxUnitFun = true)
    val productManager = mockk<ProductManager>(relaxed = true, relaxUnitFun = true)
    val main = mockk<StockChecker>(relaxUnitFun = true)

    init {
        every { productController.load(any()) } returns true
    }

    @Nested
    inner class AddCommandTest {
        val addCommand = AddCommand(logger, productManager, productController)

        @Test
        fun `test add command`() {
            addCommand.execute(listOf(Website.PCDIGA.websiteUrl))

            verify {
                productManager += any()
            }
        }
    }

    @Nested
    inner class ExitCommandTest {
        val exitCommand = ExitCommand(main)

        @Test
        fun `test exit command`() {
            exitCommand.execute(listOf())

            verify {
                main.exit()
            }
        }
    }

    @Nested
    inner class RemoveCommandTest {
        val removeCommand = RemoveCommand(logger, productManager)

        init {
            every { productManager.size() } returns 1
        }

        @Test
        fun `test remove command`() {
            removeCommand.execute(listOf("0"))

            verify {
                productManager.remove(0)
            }
        }
    }

    @Nested
    inner class ListCommandTest {
        val listCommand = ListCommand(logger, productManager)

        init {
            every { productManager.empty() } returns false
        }

        @Test
        fun `test list command`() {
            listCommand.execute(emptyList())

            verify {
                productManager.getAll()
            }
        }
    }

}