package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import androidx.compose.ui.graphics.ImageBitmap
import com.cobasendiri.kasirmudahkmp.core.data.gallery.GallerySaver
import com.cobasendiri.kasirmudahkmp.core.domain.Result

class SaveReceiptImageUseCase(
    private val gallerySaver: GallerySaver
) {
    suspend fun invoke(imageBitmap: ImageBitmap, fileName: String): Result<Boolean> {
        return try {
            val result = gallerySaver.saveImageToGallery(imageBitmap, fileName)
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}