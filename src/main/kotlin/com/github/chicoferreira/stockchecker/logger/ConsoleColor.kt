package com.github.chicoferreira.stockchecker.logger

import org.fusesource.jansi.Ansi.*

enum class ConsoleColor(private val colorCode: String) {

    BLACK(ansi().a(Attribute.RESET).fg(Color.BLACK).boldOff().toString()),
    DARK_GREEN(ansi().a(Attribute.RESET).fg(Color.GREEN).boldOff().toString()),
    DARK_BLUE(ansi().a(Attribute.RESET).fg(Color.BLUE).boldOff().toString()),
    GRAY(ansi().a(Attribute.RESET).fg(Color.WHITE).boldOff().toString()),
    DARK_GRAY(ansi().a(Attribute.RESET).fg(Color.BLACK).bold().toString()),
    GREEN(ansi().a(Attribute.RESET).fg(Color.GREEN).bold().toString()),
    DARK_RED(ansi().a(Attribute.RESET).fg(Color.RED).bold().toString()),
    RED(ansi().a(Attribute.RESET).fgBrightRed().bold().toString()),
    WHITE(ansi().a(Attribute.RESET).fg(Color.WHITE).bold().toString()),
    BOLD(ansi().a(Attribute.UNDERLINE_DOUBLE).toString()),
    STRIKETHROUGH(ansi().a(Attribute.STRIKETHROUGH_ON).toString()),
    UNDERLINE(ansi().a(Attribute.UNDERLINE).toString()),
    ITALIC(ansi().a(Attribute.ITALIC).toString()),
    RESET(ansi().a(Attribute.RESET).toString());


    override fun toString(): String {
        return colorCode
    }
}