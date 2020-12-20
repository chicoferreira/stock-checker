package com.github.chicoferreira.stockchecker.util

import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class FileManager {

    fun getOrCreateFile(fileName: String, saveDirectory: Path): File {
        val path = saveDirectory.resolve(fileName)
        val file = path.toFile()

        if (Files.notExists(path) && file.parentFile.mkdirs()) {
            file.createNewFile()
        }

        return file
    }

    fun readFromFile(file: File): String {
        BufferedReader(InputStreamReader(FileInputStream(file))).use {
            return it.lines().collect(Collectors.joining())
        }
    }

    fun saveToFile(file: File, data: String) {
        OutputStreamWriter(FileOutputStream(file), Charsets.UTF_8).use {
            it.write(data)
        }
    }
}