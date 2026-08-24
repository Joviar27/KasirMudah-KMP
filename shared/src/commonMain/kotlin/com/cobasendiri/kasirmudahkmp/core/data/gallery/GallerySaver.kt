package com.cobasendiri.kasirmudahkmp.core.data.gallery

import androidx.compose.ui.graphics.ImageBitmap

interface GallerySaver{
    suspend fun saveImageToGallery(imageBitmap: ImageBitmap, fileName: String): Boolean
}