package de.huemotion.light

import com.philips.lighting.hue.sdk.*
import com.philips.lighting.hue.sdk.utilities.PHUtilities
import com.philips.lighting.model.PHBridge
import com.philips.lighting.model.PHHueError
import com.philips.lighting.model.PHHueParsingError
import com.philips.lighting.model.PHLightState
import de.huemotion.onLightsReady
import java.util.*

class Controller {

    private var phHueSDK: PHHueSDK? = null
    private val instance: Controller

    // Start the Pushlink Authentication.
    var listener: PHSDKListener = object : PHSDKListener {

        override fun onAccessPointsFound(accessPointsList: List<PHAccessPoint>) {
            println("Found access points:")
            for (accessPoint in accessPointsList) {
                println(accessPoint.ipAddress)
            }
            for (accessPoint in accessPointsList) {
                println("Connecting to " + accessPoint.ipAddress)
                val hueSDK = PHHueSDK.getInstance()
                hueSDK.connect(accessPoint)
                hueSDK.startPushlinkAuthentication(accessPoint)
                return
            }
        }

        override fun onAuthenticationRequired(accessPoint: PHAccessPoint) {
            phHueSDK!!.startPushlinkAuthentication(accessPoint)
            println("Please push the button on your bridge")
        }

        override fun onBridgeConnected(bridge: PHBridge, username: String) {
            phHueSDK!!.selectedBridge = bridge
            phHueSDK!!.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL.toLong())
            val lastIpAddress = bridge.resourceCache.bridgeConfiguration.ipAddress
            HueProperties.storeUsername(username)
            HueProperties.storeLastIPAddress(lastIpAddress)
            HueProperties.saveProperties()

            println("Connected")
            onLightsReady()
        }

        override fun onCacheUpdated(arg0: List<Int>, arg1: PHBridge) {}

        override fun onConnectionLost(arg0: PHAccessPoint) {}

        override fun onConnectionResumed(arg0: PHBridge) {}

        override fun onError(code: Int, message: String) {

            if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                println(message)
            } else if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
            } else if (code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                println(message)
            } else if (code == PHMessageType.BRIDGE_NOT_FOUND) {
                println(message)
            }
        }

        override fun onParsingErrors(parsingErrorsList: List<PHHueParsingError>) {
            for (parsingError in parsingErrorsList) {
                //println("ParsingError : " + parsingError.message) TODO
            }
        }
    }

    init {
        this.phHueSDK = PHHueSDK.getInstance()
        this.instance = this
    }

    fun findBridges() {
        phHueSDK = PHHueSDK.getInstance()
        val sm = phHueSDK!!.getSDKService(PHHueSDK.SEARCH_BRIDGE) as PHBridgeSearchManager
        sm.search(true, true)
    }

    fun randomLights() {
        val bridge = phHueSDK!!.selectedBridge
        val cache = bridge.resourceCache

        val allLights = cache.allLights
        val rand = Random()

        for (light in allLights) {
            val lightState = PHLightState()
            lightState.hue = rand.nextInt(MAX_HUE)
            bridge.updateLightState(light, lightState) // If no bridge response is required then use this simpler form.
        }
    }

    fun listLights() {
        val lights = phHueSDK!!.selectedBridge.getResourceCache().getAllLights()
        for (light in lights) {
            println(light.name + ":     " + light.identifier)
        }
        changeLightOf("11")
    }

    fun changeLightOf(identifier: String) {
        val hueSDK = PHHueSDK.getInstance()
        val lightState = PHLightState()
        val xy = PHUtilities.calculateXYFromRGB(120, 120, 220, "LCT001")
        lightState.x = xy[0]
        lightState.y = xy[1]
        lightState.setOn(true)
        hueSDK.getSelectedBridge().updateLightState(identifier, lightState, null)
    }

    /**
     * Connect to the last known access point.
     * This method is triggered by the Connect to Bridge button but it can equally be used to automatically connect to a bridge.
     *
     */
    fun connectToLastKnownAccessPoint(): Boolean {
        val username = HueProperties.getUsername()
        val lastIpAddress = HueProperties.getLastConnectedIP()

        if (username == "" || lastIpAddress == "" || username == null || lastIpAddress == null) {
            println("Missing Last Username or Last IP.  Last known connection not found.")
            return false
        }
        val accessPoint = PHAccessPoint()
        accessPoint.ipAddress = lastIpAddress
        accessPoint.username = username
        phHueSDK!!.connect(accessPoint)
        return true
    }

    companion object {

        private val MAX_HUE = 65535
    }

}