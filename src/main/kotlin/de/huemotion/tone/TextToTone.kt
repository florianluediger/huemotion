package de.huemotion.tone

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions
import de.huemotion.ConfigManager

fun getTone(text: String): String {
    val toneAnalyzer = ToneAnalyzer("2018-06-15", ConfigManager.toneAnalyzerUser, ConfigManager.toneAnalyzerPassword)
    val toneOptions = ToneOptions.Builder()
            .text(text)
            .sentences(false)
            .build()
    return toneAnalyzer.tone(toneOptions).execute().toString()
}
