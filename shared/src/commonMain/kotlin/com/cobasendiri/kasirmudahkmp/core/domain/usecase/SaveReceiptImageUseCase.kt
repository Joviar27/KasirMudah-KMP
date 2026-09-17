package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import androidx.compose.ui.graphics.ImageBitmap
import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.data.gallery.GallerySaver
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class SaveReceiptImageUseCase(
    private val gallerySaver: GallerySaver
): BaseUseCase() {

    override val log: Logger = Logger.withTag("SaveReceiptImageUseCase")

    suspend fun invoke(imageBitmap: ImageBitmap, fileName: String): Result<Unit> {
        return try {
            val result = gallerySaver.saveImageToGallery(imageBitmap, fileName)
            when{
                result.first -> {
                    logInfo("Receipt image saved to gallery")
                    Result.Success(Unit)
                }
                result.second == "PERMISSION_DENIED" ->{
                    logWarning("Save image to gallery permission missing")
                    throw KasirMudahException.GalleryPermissionError
                }
                else -> {
                    logWarning("Failed to save image to gallery")
                    throw KasirMudahException.UnknownError(null)
                }
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}