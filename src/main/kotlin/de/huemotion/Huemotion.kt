package de.huemotion

import com.philips.lighting.hue.sdk.PHHueSDK
import de.huemotion.light.Controller
import de.huemotion.light.HueProperties


fun main(args: Array<String>) {
    val hueSDK = PHHueSDK.create()
    HueProperties.loadProperties()
    val controller = Controller()
    if (!controller.connectToLastKnownAccessPoint()) {
        // Initialize
        controller.findBridges()
        println("Please add the id of a lamp to MyHue.properties")
        return
    }
    hueSDK.getNotificationManager().registerSDKListener(controller.listener);

    //val text = getText()
    //println("Text: $text")
    //val result = getTone(text)
    //println(result)
}
