package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.parser.WebsiteParsers
import org.fusesource.jansi.Ansi.Color
import org.fusesource.jansi.Ansi.ansi
import org.fusesource.jansi.AnsiConsole
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.fixedRateTimer

fun main(args: Array<String>) {
    AnsiConsole.systemInstall()
    println("Insert link:")
    val url = readLine() ?: throw IllegalArgumentException()

    println("Connecting to $url...")

    val websiteParser = WebsiteParsers.values().find { url.startsWith(it.websiteUrl) }

    if (websiteParser == null) {
        println("Couldn't find parser for $url.")
        return
    }

    val timer = fixedRateTimer("stock-checker-timer", false, 0, 5000) {
        runCatching<Document> {
            Jsoup.connect(url).get()
        }.onSuccess {
            websiteParser.parser.parse(it).print()
        }.onFailure {
            println("Couldn't connect to $url: ${it.message}")
        }
    }

    askForCommandInput("exit") {
        timer.cancel()
        println("Exiting...")
        AnsiConsole.systemUninstall()
    }
}

fun StockCheckResult.print() {
    val builder = StringBuilder()

    val date = DateTimeFormatter
            .ofPattern("HH:mm:ss.SSS")
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault()).format(Instant.now())

    builder.append(ansi().fg(Color.WHITE).a("[$date]").reset()).append(" ")

    builder.append(ansi().fgBrightBlack().a(productName).reset()).append(" ")

    if (this.available) {
        this.availableShops.count { it.value }

        builder.append(ansi().fgGreen().a("IN STOCK").reset())
                .append(" for ")
                .append(ansi().fgGreen().a("${price}${priceCurrency}").reset())
                .append(" available in ")
                .append(ansi().fgGreen().a("${availableShops.count { it.value }}/${availableShops.count()}").reset())
                .append(" shops")

    } else {
        builder.append(ansi().fgRed().a("NOT IN STOCK").reset())
    }

    println(builder.toString())
}

fun askForCommandInput(command: String, action: () -> Unit) {
    val lastCommand = readLine()!!
    if (lastCommand.equals(command, ignoreCase = true)) {
        action()
    } else {
        println(ansi().fgRed().a("Couldn't recognize that command. Use 'exit' to stop."))
        askForCommandInput(command, action)
    }
}