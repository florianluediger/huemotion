package de.huemotion.sound

import java.io.File
import java.io.IOException
import javax.sound.sampled.*

class Recorder {

    private val format = AudioFormat(32000f, 16, 1, true, true)

    private val fileType = AudioFileFormat.Type.WAVE

    private var line: TargetDataLine? = null

    private val audioStream: AudioInputStream
        @Throws(LineUnavailableException::class)
        get() {
            val info = DataLine.Info(TargetDataLine::class.java, format)

            if (!AudioSystem.isLineSupported(info)) {
                println("Line not supported")
                System.exit(0)
            }
            line = AudioSystem.getLine(info) as TargetDataLine
            line!!.open(format)
            line!!.start()
            return AudioInputStream(line!!)
        }

    fun record(seconds: Long, fileName: String) {
        val soundFile = File(fileName)

        Thread {
            try {
                Thread.sleep(seconds * 1000)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
            println("done")
            line!!.stop()
            line!!.close()
        }.start()

        print("Starting recording...")
        try {
            val stream = audioStream
            AudioSystem.write(stream, fileType, soundFile)
        } catch (e: LineUnavailableException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
