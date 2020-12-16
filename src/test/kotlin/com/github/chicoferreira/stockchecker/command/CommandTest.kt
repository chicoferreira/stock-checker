package com.github.chicoferreira.stockchecker.command

import com.github.chicoferreira.stockchecker.logger.Logger
import io.mockk.confirmVerified
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.logging.Level

@ExtendWith(MockKExtension::class)
internal class CommandTest {

    val logger: Logger = object : Logger {
        override fun log(logLevel: Level, message: String) = Unit
    }

    val commandName = "testCommand"
    val commandToTest: Command = object : Command {
        override val name = commandName

        override fun execute(args: List<String>) = Unit
    }

    @Nested
    inner class ManagerTest {

        val commandManager = CommandManager()

        @Test
        fun registerTest() {
            commandManager.register(commandToTest)
            val get = commandManager.get(commandName.toUpperCase())

            assertNotNull(get)
        }
    }

    @Nested
    inner class ExecutorTest {

        val commandManager = CommandManager()
        val commandExecutor = CommandExecutor(logger, commandManager)

        @Test
        fun `execute command`() {
            val spyCommand = spyk(commandToTest)

            commandManager.register(spyCommand)

            val arguments: List<String> = listOf("arg1", "arg2", "arg3")

            val rawCommand = commandName + " " + arguments.joinToString(" ")
            commandExecutor.execute(rawCommand)

            verify { spyCommand.execute(arguments) }

            confirmVerified(spyCommand)
        }
    }
}