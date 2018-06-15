package de.huemotion

import de.huemotion.sound.getText
import de.huemotion.tone.getTone


fun main(args: Array<String>) {
    val text = getText()
    println("Text: $text")
    val result = getTone(text)
    println(result)
}
