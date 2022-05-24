package com.example.flowrspot.utility

import java.io.InputStreamReader

/**
 * Read json files from tests resource
 */
class MockResponseFileReader(path: String) {
    val content: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}