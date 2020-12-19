package com.github.chicoferreira.stockchecker.util

import com.github.chicoferreira.stockchecker.logger.Logger
import java.io.*
import java.nio.file.Path
import java.nio.file.Paths

class FileManager(private val logger: Logger) {

    private val saveDirectory: Path = Paths.get(System.getProperty("user.home"))

    fun getOrCreateFile(fileName: String): File {
        val path = saveDirectory.resolve(fileName)
        val file = path.toFile()

        if (!file.exists() && !file.createNewFile()) {
            logger.warning("Couldn't create file $fileName in $saveDirectory.")
        }

        return file
    }

    inline fun readFile(fileName: String, block: (BufferedReader) -> Unit) =
        BufferedReader(FileReader(getOrCreateFile(fileName))).also {
            block(it)
            it.close()
        }

    inline fun writeFile(fileName: String, block: (BufferedWriter) -> Unit) =
        BufferedWriter(FileWriter(getOrCreateFile(fileName))).also {
            block(it)
            it.close()
        }

}