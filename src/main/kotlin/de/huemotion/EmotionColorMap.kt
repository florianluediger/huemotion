package de.huemotion

import com.philips.lighting.hue.sdk.utilities.PHUtilities

val emotionColorMap = hashMapOf("anger" to PHUtilities.calculateXYFromRGB(255, 0, 0, "LCT001"),
        "fear" to PHUtilities.calculateXYFromRGB(255, 255, 0, "LCT001"),
        "joy" to PHUtilities.calculateXYFromRGB(0, 255, 0, "LCT001"),
        "sadness" to PHUtilities.calculateXYFromRGB(255, 0, 255, "LCT001"),
        "analytical" to PHUtilities.calculateXYFromRGB(255, 255, 255, "LCT001"),
        "confident" to PHUtilities.calculateXYFromRGB(255, 105, 180, "LCT001"),
        "tentative" to PHUtilities.calculateXYFromRGB(0, 255, 100, "LCT001"))