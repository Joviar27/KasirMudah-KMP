package com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.theme.OnPrimary
import com.cobasendiri.kasirmudahkmp.theme.Surface
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component.ReceiptDetailComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.view.ReceiptItem
import com.cobasendiri.kasirmudahkmp.ui.utils.FormatUtil.decimalFormat
import com.cobasendiri.kasirmudahkmp.ui.utils.dateFormat
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.InformationConfirmDialog
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.NegativeConfirmDialog
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.delete
import kasirmudah_kmp.shared.generated.resources.delete_transaction_body
import kasirmudah_kmp.shared.generated.resources.delete_transaction_title
import kasirmudah_kmp.shared.generated.resources.download_receipt
import kasirmudah_kmp.shared.generated.resources.gallery_permission_body
import kasirmudah_kmp.shared.generated.resources.gallery_permission_button
import kasirmudah_kmp.shared.generated.resources.gallery_permission_title
import kasirmudah_kmp.shared.generated.resources.ic_kasirmudah
import kasirmudah_kmp.shared.generated.resources.item_name
import kasirmudah_kmp.shared.generated.resources.price
import kasirmudah_kmp.shared.generated.resources.total
import kasirmudah_kmp.shared.generated.resources.transaction_id
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptDetailContent(
    innerPadding: PaddingValues,
    state: ReceiptDetailComponent.ReceiptDetailState,
    event: (ReceiptDetailEvent) -> Unit
){
    val formattedTotal = remember(state.totalTransaction) {
        "Rp ${state.totalTransaction.toString().decimalFormat()},00"
    }

    val formattedDate = remember(state.transactionCreatedAt) {
        state.transactionCreatedAt.dateFormat()
    }

    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    Box(Modifier.fillMaxSize()
        .background(Surface)
        .padding(innerPadding)
    ){
        Column(Modifier.padding(horizontal = 24.dp, vertical = 16.dp)){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .drawWithContent {
                        graphicsLayer.record { this@drawWithContent.drawContent() }
                        drawContent()
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
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
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(Res.string.transaction_id, state.transactionId),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
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
                isEnabled = !state.processing,
                text = stringResource(Res.string.download_receipt)
            ) {
                coroutineScope.launch {
                    val imageBitmap = graphicsLayer.toImageBitmap()
                    event.invoke(ReceiptDetailEvent.OnDownload(imageBitmap, state.transactionName))
                }
            }
        }
        if(state.showConfirmDeleteDialog != null){
            val dismissEvent = ReceiptDetailEvent.OnDismissConfirmDeleteDialog
            val transactionId = state.showConfirmDeleteDialog
            NegativeConfirmDialog(
                title = stringResource(Res.string.delete_transaction_title),
                body = stringResource(Res.string.delete_transaction_body),
                cancelButton = stringResource(Res.string.cancel),
                confirmButton = stringResource(Res.string.delete),
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onConfirm = {
                    event.invoke(ReceiptDetailEvent.OnDelete(transactionId))
                }
            )
        }
        if(state.showGalleryPermissionDialog){
            InformationConfirmDialog(
                title = stringResource(Res.string.gallery_permission_title),
                body = stringResource(Res.string.gallery_permission_body),
                confirmButton = stringResource(Res.string.gallery_permission_button),
                onDismiss = {
                    event.invoke(ReceiptDetailEvent.OnDismissConfirmDeleteDialog)
                },
                onConfirm = {
                    event.invoke(ReceiptDetailEvent.OnOpenAppSetting)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiptContentPrev(){
    ReceiptDetailContent(
        PaddingValues(0.dp),
        ReceiptDetailComponent.ReceiptDetailState(
            shopName = "Toko Madura A",
            transactionCreatedAt = 1755388800,
            transactionId = "4shisefhw48t4",
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