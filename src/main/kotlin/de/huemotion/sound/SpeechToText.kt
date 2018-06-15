package de.huemotion.sound

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions
import de.huemotion.ConfigManager
import org.json.JSONObject
import java.io.FileInputStream


fun getText(): String {
    val fileName = "Recording.wav"

    Recorder.record(10, fileName)

    val speechToText = SpeechToText(ConfigManager.speechToTextUser, ConfigManager.speechToTextPassword)

    val recognizeOptions = RecognizeOptions.Builder()
            .audio(FileInputStream(fileName))
            .contentType("audio/wav")
            .build()


    val result = speechToText.recognize(recognizeOptions).execute().toString()

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
