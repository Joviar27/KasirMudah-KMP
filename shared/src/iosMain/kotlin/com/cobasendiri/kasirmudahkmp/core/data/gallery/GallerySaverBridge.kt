package com.cobasendiri.kasirmudahkmp.core.data.gallery

object GallerySaverBridge {
    private var gallerySaver: GallerySaver? = null

    fun initialize(delegate: IOSNativeImageSaver) {
        this.gallerySaver = IOSGallerySaver(delegate)
    }

    fun getGallerySaver(): GallerySaver {
        return gallerySaver ?: error("KotlinBridge not initialized! Call initialize(delegate) in Swift.")
    }
}