package com.cobasendiri.kasirmudahkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.cobasendiri.kasirmudahkmp.ui.features.App
import com.cobasendiri.kasirmudahkmp.ui.features.RootComponent
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root = defaultComponentContext()
        val rootComponent: RootComponent by inject { parametersOf(root) }

        setContent {
            App(rootComponent)
        }
    }
}