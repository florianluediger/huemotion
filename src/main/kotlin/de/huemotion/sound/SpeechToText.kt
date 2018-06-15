package de.huemotion.sound

import org.apache.commons.io.IOUtils
import org.json.JSONObject
import java.net.Authenticator
import java.net.HttpURLConnection
import java.net.PasswordAuthentication
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

fun getText(user: String, password: String): String {
    val fileName = "Recording.wav"

    val recorder = Recorder()
    recorder.record(10, fileName)

    Authenticator.setDefault(object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(user, password.toCharArray())
        }
    })

    val url = URL("https://stream.watsonplatform.net/speech-to-text/api/v1/recognize")
    val connection = url.openConnection() as HttpURLConnection
    connection.doOutput = true
    connection.requestMethod = "POST"
    connection.setRequestProperty("Content-Type", "audio/wav")

    val path = Paths.get(fileName)
    connection.outputStream.write(Files.readAllBytes(path))
    val result = IOUtils.toString(connection.inputStream)
    return parseJSONResponse(result)
}

private fun parseJSONResponse(result: String): String {
    var json = JSONObject(result)
    var resultsArray = json.getJSONArray("results")
    json = resultsArray.getJSONObject(0)
    resultsArray = json.getJSONArray("alternatives")
    json = resultsArray.getJSONObject(0)
    return json.get("transcript").toString()
}
