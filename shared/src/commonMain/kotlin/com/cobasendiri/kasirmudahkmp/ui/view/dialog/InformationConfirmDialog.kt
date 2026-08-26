package com.cobasendiri.kasirmudahkmp.ui.view.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.theme.White
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedPrimaryButton

@Composable
fun InformationConfirmDialog(
    title: String,
    body: String,
    confirmButton: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    BaseDialog(
        onDismiss = onDismiss
    ){
        Column(
            Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = body,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(32.dp))
            RoundedPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = confirmButton
            ){
                onConfirm.invoke()
            }
        }
    }
}

@Preview
@Composable
fun InformationConfirmDialogPrev(){
    InformationConfirmDialog(
        title = "Judul Dialog",
        body = "Konten dari dialog yang kadang cukup panjang untuk ditampilkan ini cman contoh ya",
        confirmButton = "Tutup",
        onDismiss = {},
        onConfirm = {}
    )
}