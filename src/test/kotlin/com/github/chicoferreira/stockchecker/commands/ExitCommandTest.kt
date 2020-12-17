package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.StockChecker
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ExitCommandTest {

    val main = mockk<StockChecker>(relaxUnitFun = true)
    val exitCommand = ExitCommand(main)

    @Test
    fun `test exit command`() {
        exitCommand.execute(emptyList())

        verify {
            main.exit()
        }
    }
}