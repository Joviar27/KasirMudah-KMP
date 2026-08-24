package com.cobasendiri.kasirmudahkmp.ui.features.receipt.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.ui.utils.FormatUtil.decimalFormat
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.name_count
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReceiptItem(
    item: TransactionItemInfo
){
    val formattedAmount = remember(item.itemTotal) {
        "Rp ${item.itemTotal.toString().decimalFormat()},00"
    }

    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = stringResource(Res.string.name_count, item.name, item.count),
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(2f),
            text = formattedAmount,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.End
        )
    }
}
@Preview
@Composable
fun ReceiptItemPrev() {
    ReceiptItem(
        TransactionItemInfo(
            name = "Barang Nomor 15",
            itemTotal = 980000,
            count = 5
        )
    )
}