package com.github.chicoferreira.stockchecker.command

class CommandManager {

    private val commandMap: MutableMap<String, Command> = HashMap()

    fun register(command: Command) {
        commandMap[command.name.toLowerCase()] = command
    }

    fun get(commandName: String): Command? = commandMap[commandName.toLowerCase()]

}