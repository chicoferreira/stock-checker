package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.command.CommandExecutor
import com.github.chicoferreira.stockchecker.command.CommandManager
import com.github.chicoferreira.stockchecker.commands.ExitCommand
import com.github.chicoferreira.stockchecker.console.Console
import com.github.chicoferreira.stockchecker.parser.Website
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.fixedRateTimer

class StockChecker {

    private var enabled = true
    private val console = Console()
    private val commandManager = CommandManager()
    private lateinit var commandExecutor: CommandExecutor
    private lateinit var timer: Timer

    fun enable() {
        console.setup()

        commandExecutor = CommandExecutor(console, commandManager)

        console.info("Insert link:")
        val url = console.readLine()

        console.info("Connecting to $url...")

        val website = Website.values().find { it.isUrl(url) }

        if (website == null) {
            console.warning("Couldn't find parser for $url.")
            return
        }

        timer = fixedRateTimer("stock-checker-timer", false, 0, 1000, StockCheckerTask(url, website, console))

        commandManager.register(ExitCommand(this))

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