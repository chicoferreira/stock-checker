package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.logger.ConsoleColor
import com.github.chicoferreira.stockchecker.logger.Logger
import com.github.chicoferreira.stockchecker.logger.SimpleLogger
import com.github.chicoferreira.stockchecker.parser.WebsiteParsers
import org.fusesource.jansi.AnsiConsole
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.concurrent.fixedRateTimer

val logger: Logger = SimpleLogger()

fun main(args: Array<String>) {
    AnsiConsole.systemInstall()
    logger.info("Insert link:")
    val url = readLine() ?: throw IllegalArgumentException()

    logger.info("Connecting to $url...")

    val websiteParser = WebsiteParsers.values().find { url.startsWith(it.websiteUrl) }

    if (websiteParser == null) {
        logger.warning("Couldn't find parser for $url.")
        return
    }

    val timer = fixedRateTimer("stock-checker-timer", false, 0, 5000) {
        runCatching<Document> {
            Jsoup.connect(url).get()
        }.onSuccess {
            websiteParser.parser.parse(it).print()
        }.onFailure {
            logger.warning("Couldn't connect to $url: ${it.message}")
        }
    }

    askForCommandInput("exit") {
        timer.cancel()
        logger.info("Exiting...")
        AnsiConsole.systemUninstall()
    }
}

fun StockCheckResult.print() {
    val builder = StringBuilder()

    builder.append("${ConsoleColor.DARK_GRAY}$productName ")

    if (this.available) {
        builder.append("${ConsoleColor.GREEN}IN STOCK ${ConsoleColor.RESET}for ${ConsoleColor.GREEN}${price}")
        builder.append("${priceCurrency}${ConsoleColor.RESET} available in ${ConsoleColor.GREEN}")
        builder.append("${availableShops.count { it.value }}/${availableShops.count()}${ConsoleColor.RESET} shops")
    } else {
        builder.append("${ConsoleColor.RED}NOT IN STOCK")
    }

    logger.info(builder.toString())
}

fun askForCommandInput(command: String, action: () -> Unit) {
    val lastCommand = readLine()!!
    if (lastCommand.equals(command, ignoreCase = true)) {
        action()
    } else {
        logger.info("${ConsoleColor.RED}Couldn't recognize that command. Use 'exit' to stop.")
        askForCommandInput(command, action)
    }
}