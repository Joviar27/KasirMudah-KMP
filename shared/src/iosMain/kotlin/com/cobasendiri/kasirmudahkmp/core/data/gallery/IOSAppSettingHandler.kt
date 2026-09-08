package com.cobasendiri.kasirmudahkmp.core.data.gallery

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class IOSAppSettingHandler: AppSettingHandler {
    override fun openAppSettings() {
        val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
        if (settingsUrl != null) {
            val application = UIApplication.sharedApplication
            if (application.canOpenURL(settingsUrl)) {
                application.openURL(
                    url = settingsUrl,
                    options = emptyMap<Any?, Any>(),
                    completionHandler = null
                )
            }
        }
    }
}