package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import androidx.compose.ui.graphics.ImageBitmap
import com.cobasendiri.kasirmudahkmp.core.data.gallery.GallerySaver
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException

class SaveReceiptImageUseCase(
    private val gallerySaver: GallerySaver
) {
    suspend fun invoke(imageBitmap: ImageBitmap, fileName: String): Result<Unit> {
        return try {
            val result = gallerySaver.saveImageToGallery(imageBitmap, fileName)
            when{
                result.first -> Result.Success(Unit)
                result.second == "PERMISSION_DENIED" -> throw KasirMudahException.GalleryPermissionError
                else -> throw KasirMudahException.UnknownError(null)
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}