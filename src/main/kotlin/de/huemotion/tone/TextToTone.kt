package de.huemotion.tone

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions
import de.huemotion.ConfigManager
import org.json.JSONObject

fun getTone(text: String): String {
    val toneAnalyzer = ToneAnalyzer("2018-06-15", ConfigManager.toneAnalyzerUser, ConfigManager.toneAnalyzerPassword)
    val toneOptions = ToneOptions.Builder()
            .text(text)
            .sentences(false)
            .build()
    val result = toneAnalyzer.tone(toneOptions).execute().toString()

    return parseJSONResponse(result)
}

private fun parseJSONResponse(result: String): String {
    val json = JSONObject(result)
    val documentTone = json.getJSONObject("document_tone")
    val resultsArray = documentTone.getJSONArray("tones")
    val toneObject = resultsArray.getJSONObject(0)
    return toneObject.get("tone_id").toString()
}
