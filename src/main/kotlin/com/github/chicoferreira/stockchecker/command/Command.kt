package com.github.chicoferreira.stockchecker.command

interface Command {

    val name: String

    fun execute(args: List<String>)

}