package com.github.chicoferreira.stockchecker.command

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CommandManagerTest {

    val commandManager = CommandManager()
    val commandName = "testCommand"
    val command = mockk<Command>()

    @Test
    fun registerTest() {
        every { command.name } returns commandName

        commandManager.register(command)

        assertNotNull(commandManager.get(commandName.toUpperCase()))

        commandManager.unregister(command)
    }
}