package com.cobasendiri.kasirmudahkmp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

interface ImagePickerLauncher {
    fun launch()
}

@Composable
fun rememberImagePickerLauncher(
    onResult: (ByteArray?) -> Unit
): ImagePickerLauncher{
    val scope = rememberCoroutineScope()

    val imageLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = {
            onResult.invoke(it.firstOrNull())
        }
    )

    return remember(imageLauncher){
        object : ImagePickerLauncher {
            override fun launch() {
                imageLauncher.launch()
            }
        }
    }
}