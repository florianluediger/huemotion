package de.huemotion

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Properties

object ConfigManager {

    private val filePath = System.getProperty("user.dir") + File.separator + "Credentials.cfg"

    private val properties: Properties = Properties()

    val speechToTextUser: String
        get() = properties.getProperty("SpeechToTextUser")

    val speechToTextPassword: String
        get() = properties.getProperty("SpeechToTextPassword")

    val toneAnalyzerUser: String
        get() = properties.getProperty("ToneAnalyzerUser")

    val toneAnalyzerPassword: String
        get() = properties.getProperty("ToneAnalyzerPassword")

    init {
        if (File(filePath).exists()) {
            try {
                properties.load(FileInputStream(filePath))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            properties["SpeechToTextUser"] = ""
            properties["SpeechToTextPassword"] = ""
            properties["ToneAnalyzerUser"] = ""
            properties["ToneAnalyzerPassword"] = ""
            try {
                properties.store(FileOutputStream(filePath), null)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

}