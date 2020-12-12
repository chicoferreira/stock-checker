package com.github.chicoferreira.stockchecker.command

import com.github.chicoferreira.stockchecker.console.ConsoleColor
import com.github.chicoferreira.stockchecker.logger.Logger

class CommandExecutor(private val logger: Logger, private val commandManager: CommandManager) {

    fun execute(rawCommand: String) {
        if (rawCommand.isEmpty()) {
            return
        }

        val args = rawCommand.trim().split(" ")

        if (args.isEmpty()) {
            return
        }

        val commandName = args[0]

        val command = commandManager.get(commandName)
        if (command == null) {
            logger.warning("Couldn't recognize that command. Use 'exit' to stop.")
            return
        }

        command.execute(args.subList(1, args.size))
    }

}