package com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.theme.Negative
import com.cobasendiri.kasirmudahkmp.theme.Secondary
import com.cobasendiri.kasirmudahkmp.theme.White
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.delete
import kasirmudah_kmp.shared.generated.resources.done
import kasirmudah_kmp.shared.generated.resources.ic_delete_white_round
import kasirmudah_kmp.shared.generated.resources.ic_right_arrow_white_round
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FloatingAction(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onDone: () -> Unit
) {
    Row(modifier.width(280.dp)
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(32.dp))
    ) {
        Row(Modifier.clip(RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp))
            .clickable(onClick = onDelete)
            .background(Negative)
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(Res.drawable.ic_delete_white_round),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(5f),
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.delete),
                style = MaterialTheme.typography.bodyMedium
                    .copy(fontWeight = FontWeight.SemiBold, color = White)
            )
        }
        Row(Modifier.clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
            .clickable(onClick = onDone)
            .background(Secondary)
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(5f),
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.done),
                style = MaterialTheme.typography.bodyMedium
                    .copy(fontWeight = FontWeight.SemiBold)
            )
            Image(
                modifier = Modifier.weight(1f)
                    .size(17.dp),
                painter = painterResource(Res.drawable.ic_right_arrow_white_round),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun FloatingActionPrev() {
    FloatingAction(
        onDelete = {}
    ){}
}