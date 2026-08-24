package com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.view.ReceiptItem
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.theme.OnPrimary
import com.cobasendiri.kasirmudahkmp.theme.Surface
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component.ReceiptDraftComponent
import com.cobasendiri.kasirmudahkmp.ui.utils.FormatUtil.decimalFormat
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedPrimaryButton
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.ic_kasirmudah
import kasirmudah_kmp.shared.generated.resources.item_name
import kasirmudah_kmp.shared.generated.resources.price
import kasirmudah_kmp.shared.generated.resources.save
import kasirmudah_kmp.shared.generated.resources.total
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptDraftContent(
    innerPadding: PaddingValues,
    state: ReceiptDraftComponent.ReceiptDraftState,
    event: (ReceiptDraftEvent) -> Unit
){
    val formattedTotal = remember(state.totalTransaction) {
        "Rp ${state.totalTransaction.toString().decimalFormat()},00"
    }

    val scrollState = rememberScrollState()

    Box(Modifier.fillMaxSize()
        .background(Surface)
        .padding(innerPadding)
    ){
        Column(Modifier.padding(vertical = 16.dp, horizontal = 24.dp)){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))
                Image(
                    modifier = Modifier.size(180.dp,25.dp),
                    painter = painterResource(Res.drawable.ic_kasirmudah),
                    contentDescription = null,
                    alignment = Alignment.CenterStart
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = state.shopName,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(36.dp))
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.item_name),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stringResource(Res.string.price),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = OnPrimary
                )
                Spacer(Modifier.height(10.dp))
                state.transactionShopItems.forEach { item ->
                    Spacer(Modifier.height(6.dp))
                    ReceiptItem(item)
                    Spacer(Modifier.height(6.dp))
                }
                Spacer(Modifier.height(36.dp))
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.total),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = formattedTotal,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = OnPrimary
                )
            }
            Spacer(Modifier.height(16.dp))
            RoundedPrimaryButton(
                text = stringResource(Res.string.save)
            ) {
                event.invoke(ReceiptDraftEvent.OnSave)
            }
            Spacer(Modifier.height(8.dp))
            RoundedOutlinedButton(
                text = stringResource(Res.string.cancel)
            ) {
                event.invoke(ReceiptDraftEvent.OnNavigateBack)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiptContentPrev(){
    ReceiptDraftContent(
        innerPadding = PaddingValues(0.dp),
        ReceiptDraftComponent.ReceiptDraftState(
            shopName = "Toko Madura A",
            transactionShopItems = MutableList(6) {
                TransactionItemInfo(
                    name = "Barang Nomor $it",
                    itemTotal = 980000,
                    count = 5
                )
            },
            totalTransaction = 1500225
        )
    ){}
}