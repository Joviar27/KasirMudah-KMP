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
import platform.Foundation.NSOrderedSet
import platform.Foundation.NSPredicate
import platform.Foundation.dataWithBytes
import platform.Foundation.orderedSetWithObject
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAssetCollection
import platform.Photos.PHAssetCollectionChangeRequest
import platform.Photos.PHAssetCollectionSubtypeAny
import platform.Photos.PHAssetCollectionTypeAlbum
import platform.Photos.PHFetchOptions
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIImage
import kotlin.coroutines.resume

class IOSGallerySaver: GallerySaver {
    override suspend fun saveImageToGallery(
        imageBitmap: ImageBitmap,
        fileName: String
    ): Boolean = suspendCancellableCoroutine{ continuation ->
        val skiaBitmap = imageBitmap.asSkiaBitmap()
        val skiaImage = Image.makeFromBitmap(skiaBitmap)
        val data = skiaImage.encodeToData(EncodedImageFormat.PNG)
            ?: return@suspendCancellableCoroutine continuation.resume(false)

        val nsData = data.bytes.toNSData()
        val image = UIImage(data = nsData)

        val albumName = "KasirMudah"
        PHPhotoLibrary.sharedPhotoLibrary().performChanges({
            val fetchOptions = PHFetchOptions()
            fetchOptions.predicate = NSPredicate.predicateWithFormat("title = %@", albumName)
            val collections = PHAssetCollection.fetchAssetCollectionsWithType(
                PHAssetCollectionTypeAlbum,
                PHAssetCollectionSubtypeAny,
                fetchOptions
            )

            val albumChangeRequest = if (collections.count > 0UL) {
                val existingAlbum = collections.firstObject as PHAssetCollection
                PHAssetCollectionChangeRequest.changeRequestForAssetCollection(existingAlbum)
            } else {
                PHAssetCollectionChangeRequest.creationRequestForAssetCollectionWithTitle(albumName)
            }

            val assetChangeRequest = PHAssetChangeRequest.creationRequestForAssetFromImage(image)
            val assetPlaceholder = assetChangeRequest.placeholderForCreatedAsset

            if (assetPlaceholder != null) {
                val assetsSet = NSOrderedSet.orderedSetWithObject(assetPlaceholder)
                albumChangeRequest?.addAssets(assetsSet)
            }
        }, completionHandler = { success, error ->
            if (!success) {
                println("Error saving image: ${error?.localizedDescription}")
            }
            continuation.resume(success)
        })
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ByteArray.toNSData(): NSData = this.usePinned { pinned ->
        NSData.dataWithBytes(pinned.addressOf(0), this.size.toULong())
    }
}