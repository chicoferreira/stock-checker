package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.command.CommandExecutor
import com.github.chicoferreira.stockchecker.command.CommandManager
import com.github.chicoferreira.stockchecker.commands.AddCommand
import com.github.chicoferreira.stockchecker.commands.ExitCommand
import com.github.chicoferreira.stockchecker.commands.ListCommand
import com.github.chicoferreira.stockchecker.console.Console
import com.github.chicoferreira.stockchecker.product.ProductController
import com.github.chicoferreira.stockchecker.product.ProductManager
import java.util.*
import kotlin.concurrent.fixedRateTimer

class StockChecker {

    private var enabled = true
    private val console = Console()
    private val commandManager = CommandManager()
    private val commandExecutor = CommandExecutor(console, commandManager)
    private val productManager = ProductManager()
    private val productController = ProductController(console)

    private lateinit var timer: Timer

    fun enable() {
        console.setup()

        if (productManager.empty()) {
            console.info("No product is loaded. Use 'add <link>' to add a new product.")
        }

        // TODO: change delay back to 1000
        timer = fixedRateTimer(
            "stock-checker-timer", false, 0, 10000, StockCheckerTask(productManager, productController, console)
        )

        commandManager.register(ExitCommand(this))
        commandManager.register(AddCommand(console, productManager, productController))
        commandManager.register(ListCommand(console, productManager))

        while (enabled) {
            commandExecutor.execute(console.readLine())
        }

    }

    fun exit() {
        timer.cancel()
        console.info("Exiting...")

        enabled = false
    }
}