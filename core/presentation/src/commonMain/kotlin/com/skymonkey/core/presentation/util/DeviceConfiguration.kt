package com.skymonkey.core.presentation.util

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

/**
 * Utility Composable function to get the current device configuration.
 * Helps distinguishing between portrait, landscape, mobile, tablet and desktop layouts.
 */
@Composable
fun currentDeviceConfiguration(): DeviceConfiguration {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
}

enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            return with(windowSizeClass) {
                when {
                    // check if we are in mobile portrait layout
                    minWidthDp < WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp >= WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND ->
                                MOBILE_PORTRAIT
                    // check if we are in mobile landscape layout
                    minWidthDp >= WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp < WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND ->
                                MOBILE_LANDSCAPE
                    // check if we are in tablet portrait layout
                    minWidthDp in WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND..WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp >= WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND ->
                                TABLET_PORTRAIT
                    // check if we are in tablet landscape layout
                    minWidthDp >= WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp in WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND..WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND ->
                                TABLET_LANDSCAPE
                    else -> DESKTOP
                }
            }
        }
    }
}