package com.cobasendiri.kasirmudahkmp.core.data.gallery

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AndroidGallerySaver(
    private val context: Context
): GallerySaver {

    private val appContext = context.applicationContext

    override suspend fun saveImageToGallery(
        imageBitmap: ImageBitmap,
        fileName: String
    ): Boolean = withContext(Dispatchers.IO) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return@withContext false

        val contentResolver = appContext.contentResolver
        val imageName = "kasirmudah_${fileName}_${System.currentTimeMillis()}.png"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/KasirMudah")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ) ?: return@withContext false

        return@withContext try {
            contentResolver.openOutputStream(imageUri)?.use { stream ->
                val androidBitmap = imageBitmap.asAndroidBitmap()
                if (!androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                    throw IOException("Failed to compress bitmap")
                }
            } ?: throw IOException("Failed to open output stream")

            val updateValues = ContentValues().apply {
                put(MediaStore.Images.Media.IS_PENDING, 0)
            }
            contentResolver.update(imageUri, updateValues, null, null)

            true
        } catch (e: Exception) {
            e.printStackTrace()
            contentResolver.delete(imageUri, null, null)
            false
        }
    }
}