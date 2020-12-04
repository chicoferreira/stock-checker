package com.github.chicoferreira.stockchecker.logger

import java.util.logging.Level

interface Logger {

    fun info(message: String) = log(Level.INFO, message)

    fun warning(message: String) = log(Level.WARNING, message)

    fun severe(message: String) = log(Level.SEVERE, message)

    fun debug(message: String) = log(Level.CONFIG, message)

    fun log(logLevel: Level, message: String)

    fun log(logLevel: Level, message: String, throwable: Throwable) = log(logLevel, message)

}