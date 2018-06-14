package de.huemotion

import de.huemotion.sound.getText
import khttp.structures.authorization.BasicAuthorization
import java.net.URLEncoder


fun main(args: Array<String>) {
    val config = ConfigManager()
    val text = getText(config.speechToTextUser, config.speechToTextPassword)
    println("Text: $text")
    val url = "https://gateway.watsonplatform.net/tone-analyzer/api/v3/tone?version=2017-09-21&sentences=false&text=" + URLEncoder.encode(text, "UTF-8")
    print(khttp.get(url = url, auth = BasicAuthorization(config.toneAnalyzerUser, config.toneAnalyzerPassword)).jsonObject)
}
