package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.command.CommandExecutor
import com.github.chicoferreira.stockchecker.command.CommandManager
import com.github.chicoferreira.stockchecker.commands.ExitCommand
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.logger.SimpleLogger
import com.github.chicoferreira.stockchecker.parser.WebsiteParsers
import org.fusesource.jansi.AnsiConsole
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*
import kotlin.concurrent.fixedRateTimer

class StockChecker {

    private var enabled = true
    private val logger: Logger = SimpleLogger()
    private var timer: Timer? = null
    private val commandManager = CommandManager()
    private val commandExecutor = CommandExecutor(logger, commandManager)

    fun enable() {
        AnsiConsole.systemInstall()
        logger.info("Insert link:")
        val url = readLine() ?: throw IllegalArgumentException()

        logger.info("Connecting to $url...")

        val websiteParser = WebsiteParsers.values().find { it.isUrl(url) }

        if (websiteParser == null) {
            logger.warning("Couldn't find parser for $url.")
            return
        }

        timer = fixedRateTimer("stock-checker-timer", false, 0, 5000) {
            runCatching<Document> {
                Jsoup.connect(url).get()
            }.onSuccess {
                websiteParser.parser.parse(it).buildRender().also { result -> logger.info(result) }
            }.onFailure {
                logger.warning("Couldn't connect to $url: ${it.message}")
            }
        }

        commandManager.register(ExitCommand(this))

        while (enabled) {
            askForCommandInput()
        }
    }

    fun exit() {
        timer?.cancel()
        logger.info("Exiting...")
        AnsiConsole.systemUninstall()

        enabled = false
    }

    private fun askForCommandInput() {
        val command = readLine() ?: return
        commandExecutor.execute(command)
    }

}