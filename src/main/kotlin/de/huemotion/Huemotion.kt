package de.huemotion

import com.philips.lighting.hue.sdk.PHHueSDK
import de.huemotion.light.Controller
import de.huemotion.light.HueProperties
import de.huemotion.sound.getText
import de.huemotion.tone.getTone

val controller = Controller()

fun main(args: Array<String>) {
    val hueSDK = PHHueSDK.create()
    HueProperties.loadProperties()
    hueSDK.getNotificationManager().registerSDKListener(controller.listener);
    if (!controller.connectToLastKnownAccessPoint()) {
        // Initialize
        println("Looking for access points")
        controller.findBridges()
        println("Since this is your first time you might want to add a light id")
        controller.listLights()
        System.exit(0)
    }
}

fun onLightsReady() {
    val lightID = HueProperties.getLightID()
    if (lightID == null || lightID == "") {
        println("Light id misssing. Please add one to the properties file and restart.")
        HueProperties.storeLightID("")
        controller.listLights()
        System.exit(0)
    }
    //val text = getText()
    //println("Text: $text")
    //val result = getTone(text)
    //println(result)
    controller.changeLightOf(lightID)
}
