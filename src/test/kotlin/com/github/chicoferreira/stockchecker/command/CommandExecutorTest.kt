package com.github.chicoferreira.stockchecker.command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class CommandExecutorTest {

    val commandName = "testCommand"

    val commandManager = CommandManager()
    val commandExecutor = CommandExecutor(mockk(relaxUnitFun = true), commandManager)

    @Test
    fun `execute command`() {
        val command = mockk<Command>(relaxUnitFun = true)
        every { command.name } returns commandName

        commandManager.register(command)

        val arguments: List<String> = listOf("arg1", "arg2", "arg3")

        commandExecutor.execute(commandName + " " + arguments.joinToString(" "))

        verify {
            command.execute(arguments)
        }

        commandManager.unregister(command)
    }

    @Test
    fun `test empty command`() {
        val command = mockk<Command>(relaxed = true, relaxUnitFun = true)
        every { command.name } returns commandName

        commandManager.register(command)

        commandExecutor.execute("")

        verify(exactly = 0) {
            command.execute(any())
        }

        commandManager.unregister(command)
    }
}