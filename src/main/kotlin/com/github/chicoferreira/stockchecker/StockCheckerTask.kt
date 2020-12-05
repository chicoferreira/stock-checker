package com.github.chicoferreira.stockchecker

import com.github.chicoferreira.stockchecker.console.Console
import com.github.chicoferreira.stockchecker.parser.Website
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class StockCheckerTask(val url: String, val website: Website, val console: Console) : (TimerTask) -> Unit {

    val lastAccessMap: MutableMap<Website, Long> = ConcurrentHashMap()

    override fun invoke(p1: TimerTask) {
        val lastTimeAccessed = lastAccessMap[website]
        if (lastTimeAccessed != null && isInDelay(lastTimeAccessed)) {
            return
        }

        lastAccessMap[website] = System.currentTimeMillis()

        runCatching<Document> {
            Jsoup.connect(url).get()
        }.onSuccess {
            website.parser.parse(it).buildRender().also { result -> console.info(result) }
        }.onFailure {
            console.warning("Couldn't connect to $url: ${it.message}")
        }
    }

    private fun isInDelay(lastTimeAccessed: Long): Boolean =
        System.currentTimeMillis() - lastTimeAccessed < website.delayInSeconds * 1000
}