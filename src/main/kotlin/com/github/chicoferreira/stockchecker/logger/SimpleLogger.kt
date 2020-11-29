package com.github.chicoferreira.stockchecker.logger

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level

open class SimpleLogger : Logger {

    override fun log(logLevel: Level, message: String) {
        val date = DateTimeFormatter
                .ofPattern("HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault()).format(Instant.now())

        val prefix = when (logLevel) {
            Level.INFO -> "[INFO]"
            Level.WARNING -> "[WARNING]"
            Level.SEVERE -> "[SEVERE]"
            Level.CONFIG -> "[DEBUG]"
            else -> "[UNKNOWN]"
        }

        println("[$date] $prefix $message")
    }
}