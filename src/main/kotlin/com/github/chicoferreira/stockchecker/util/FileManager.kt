package com.github.chicoferreira.stockchecker.util

import java.io.*
import java.nio.file.Path
import java.nio.file.Paths

class FileManager {

    private val saveDirectory: Path = Paths.get(System.getProperty("user.home"))

    fun getOrCreateFile(fileName: String): File {
        val path = saveDirectory.resolve(fileName)
        val file = path.toFile()

        if (!file.exists() && !file.createNewFile()) {
            throw IOException("Couldn't create file $fileName in $saveDirectory.")
        }

        return file
    }

    inline fun <T> readFile(fileName: String, block: (BufferedReader) -> T) =
        BufferedReader(FileReader(getOrCreateFile(fileName))).let { bufferedReader ->
            block(bufferedReader).also {
                bufferedReader.close()
            }
        }

    inline fun <T> writeFile(fileName: String, block: (BufferedWriter) -> T) =
        BufferedWriter(FileWriter(getOrCreateFile(fileName))).let { bufferedReader ->
            block(bufferedReader).also {
                bufferedReader.close()
            }
        }
}