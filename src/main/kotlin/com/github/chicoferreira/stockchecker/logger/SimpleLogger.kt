package com.github.chicoferreira.stockchecker.logger

import org.jline.reader.LineReader
import org.jline.reader.impl.LineReaderImpl
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level

class SimpleLogger(private val reader: LineReader) : Logger {

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