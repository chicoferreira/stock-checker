package com.github.chicoferreira.stockchecker.console

import com.github.chicoferreira.stockchecker.logger.Logger
import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder
import org.jline.terminal.TerminalBuilder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level

class Console : Logger {

    companion object {
        const val PROMPT = "> "
    }

    private lateinit var reader: LineReader

    fun setup() {
        val terminal = TerminalBuilder.builder().jansi(true).name("Stock Checker").build()
        reader = LineReaderBuilder.builder().terminal(terminal).build()
    }

    fun readLine(): String {
        return reader.readLine(PROMPT)
    }

    override fun log(logLevel: Level, message: String) {
        val date = DateTimeFormatter
                .ofPattern("HH:mm:ss")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault()).format(Instant.now())

        val prefix = when (logLevel) {
            Level.INFO -> "${ConsoleColor.GREEN}[INFO]"
            Level.WARNING -> "${ConsoleColor.RED}[WARNING]"
            Level.SEVERE -> "${ConsoleColor.DARK_RED}[SEVERE]"
            Level.CONFIG -> "${ConsoleColor.DARK_BLUE}[DEBUG]"
            else -> "${ConsoleColor.DARK_GRAY}[UNKNOWN]"
        }

        reader.printAbove("${ConsoleColor.DARK_GRAY}[$date] $prefix${ConsoleColor.RESET} $message${ConsoleColor.RESET}")
    }

}