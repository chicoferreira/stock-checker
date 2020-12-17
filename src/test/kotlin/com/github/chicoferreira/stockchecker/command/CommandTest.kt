package com.github.chicoferreira.stockchecker.command

import com.github.chicoferreira.stockchecker.logger.Logger
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CommandTest {

    val logger: Logger = mockk(relaxed = true, relaxUnitFun = true)

    val commandName = "testCommand"

    @Nested
    inner class ManagerTest {

        val commandManager = CommandManager()

        val command: Command = object : Command {
            override val name = commandName

            override fun execute(args: List<String>) = Unit
        }

        @Test
        fun registerTest() {
            commandManager.register(command)
            val get = commandManager.get(commandName.toUpperCase())

            assertNotNull(get)
        }
    }

    @Nested
    inner class ExecutorTest {

        val commandManager = CommandManager()
        val commandExecutor = CommandExecutor(logger, commandManager)

        val command = mockk<Command>(relaxUnitFun = true)

        @BeforeEach
        fun setupCommand() {
            every { command.name } returns commandName

            commandManager.register(command)
        }

        @Test
        fun `execute command`() {
            val arguments: List<String> = listOf("arg1", "arg2", "arg3")

            val rawCommand = commandName + " " + arguments.joinToString(" ")
            commandExecutor.execute(rawCommand)

            verify {
                command.execute(arguments)
            }
        }

        @Test
        fun `test empty command`() {
            commandExecutor.execute("")

            verify(exactly = 0) {
                command.execute(any())
            }
        }
    }
}