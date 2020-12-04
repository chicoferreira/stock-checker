package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.command.CommandExecutor
import com.github.chicoferreira.stockchecker.command.CommandManager
import com.github.chicoferreira.stockchecker.commands.ExitCommand
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.logger.SimpleLogger
import com.github.chicoferreira.stockchecker.parser.WebsiteParsers
import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder
import org.jline.terminal.TerminalBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*
import kotlin.concurrent.fixedRateTimer

class StockChecker {

    companion object {
        const val PROMPT = "> "
    }

    private var enabled = true
    private val commandManager = CommandManager()
    private lateinit var commandExecutor: CommandExecutor
    private lateinit var logger: Logger
    private lateinit var timer: Timer
    private lateinit var reader: LineReader

    fun enable() {
        setupConsole()

        commandExecutor = CommandExecutor(logger, commandManager)

        logger.info("Insert link:")
        val url = reader.readLine(PROMPT) ?: throw IllegalArgumentException()

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
        timer.cancel()
        logger.info("Exiting...")

        enabled = false
    }

    private fun setupConsole() {
        val terminal = TerminalBuilder.builder().jansi(true).name("Stock Checker").build()
        reader = LineReaderBuilder.builder().terminal(terminal).build()

        logger = SimpleLogger(reader)
    }

    private fun askForCommandInput() {
        val command = reader.readLine(PROMPT) ?: return
        commandExecutor.execute(command)
    }

}