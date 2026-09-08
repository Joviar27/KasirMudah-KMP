package com.cobasendiri.kasirmudahkmp.core.data.gallery

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.Photos.PHAccessLevelAddOnly
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIImage
import kotlin.coroutines.resume

class IOSGallerySaver(
    private val nativeSaver: IOSNativeImageSaver
): GallerySaver {
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun saveImageToGallery(
        imageBitmap: ImageBitmap,
        fileName: String
    ): Pair<Boolean,String?> = suspendCancellableCoroutine{ continuation ->
        val skiaBitmap = imageBitmap.asSkiaBitmap()
        val skiaImage = Image.makeFromBitmap(skiaBitmap)
        val data = skiaImage.encodeToData(EncodedImageFormat.PNG)
            ?: return@suspendCancellableCoroutine continuation.resume(Pair(false, null))

        val nsData = data.bytes.toNSData()
        val image = UIImage(data = nsData)

        PHPhotoLibrary.requestAuthorizationForAccessLevel(PHAccessLevelAddOnly) { status ->
            when(status){
                PHAuthorizationStatusAuthorized, PHAuthorizationStatusLimited, PHAuthorizationStatusNotDetermined ->{
                    nativeSaver.save(image){ isSuccess, error ->
                        if (isSuccess) {
                            continuation.resume(Pair(true, null))
                        } else {
                            continuation.resume(Pair(false, error?.localizedDescription))
                        }
                    }
                }
                PHAuthorizationStatusDenied, PHAuthorizationStatusRestricted ->{
                    continuation.resume(Pair(false, "PERMISSION_DENIED"))
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ByteArray.toNSData(): NSData = this.usePinned { pinned ->
        NSData.dataWithBytes(pinned.addressOf(0), this.size.toULong())
    }
}