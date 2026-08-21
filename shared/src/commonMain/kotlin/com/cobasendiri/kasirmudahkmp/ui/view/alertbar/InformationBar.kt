package com.cobasendiri.kasirmudahkmp.ui.view.alertbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.theme.Tertiary
import com.cobasendiri.kasirmudahkmp.theme.White
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.ic_info_outline_24_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun InformationBar(
    modifier: Modifier = Modifier,
    imageStart: Painter = painterResource(Res.drawable.ic_info_outline_24_white),
    imageBackground: Color = Tertiary,
    message: String,
) {
    Surface(
        modifier.fillMaxWidth()
            .padding(8.dp)
            .background(White, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(Modifier.background(White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier = Modifier.background(imageBackground, CircleShape),
                painter = imageStart,
                contentDescription = null
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = message.take(140),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun InformationBarPrev() {
    InformationBar(message = "Example of a message to be displayed")
}