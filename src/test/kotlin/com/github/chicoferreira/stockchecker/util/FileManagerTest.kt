package com.github.chicoferreira.stockchecker.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths

internal class FileManagerTest {

    val path: Path = Paths.get("", "build/test-tmp")
    val fileName = "test-file.txt"

    val fileManager = FileManager()

    @Test
    fun readFromAndSaveToFile() {
        val file = fileManager.getOrCreateFile(fileName, path)

        assertTrue(file.exists())

        fileManager.saveToFile(file, "test-string")
        assertEquals(fileManager.readFromFile(file), "test-string")

        file.delete()
        file.parentFile.delete()
    }
}