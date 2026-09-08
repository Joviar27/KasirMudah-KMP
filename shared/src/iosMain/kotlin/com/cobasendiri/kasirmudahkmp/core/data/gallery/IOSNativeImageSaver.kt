package com.cobasendiri.kasirmudahkmp.core.data.gallery

import platform.Foundation.NSError
import platform.UIKit.UIImage

interface IOSNativeImageSaver {
    fun save(uiImage: UIImage, onResult: (isSuccess: Boolean, error: NSError?) -> Unit)
}