package de.huemotion

import khttp.structures.authorization.BasicAuthorization
import java.net.URLEncoder

fun main(args: Array<String>) {
    val config = ConfigManager()
    val text = "Team, I know that times are tough! Product sales have been disappointing for the past three quarters. We have a competitive product, but we need to do a better job of selling it!"
    val url = "https://gateway.watsonplatform.net/tone-analyzer/api/v3/tone?version=2017-09-21&sentences=false&text=" + URLEncoder.encode(text, "UTF-8")
    print(khttp.get(url = url, auth= BasicAuthorization(config.user, config.password)).jsonObject)
}
