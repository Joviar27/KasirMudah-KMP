package com.cobasendiri.kasirmudahkmp.features.shop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeTabScreen() {

    Scaffold(Modifier.fillMaxSize()){
        Box(Modifier.fillMaxSize()){
            Text(
                "Home Tab Screen",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun HomeTabScreenPrev() {
    HomeTabScreen()
}