package com.github.chicoferreira.stockchecker.commands

import com.github.chicoferreira.stockchecker.StockChecker
import com.github.chicoferreira.stockchecker.command.Command

class ExitCommand(private val main: StockChecker) : Command {
    override val name: String = "exit"

    override fun execute(args: List<String>) {
        main.exit()
    }
}