package com.cobasendiri.kasirmudahkmp.ui.features.receipt.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudahkmp.theme.Primary
import com.cobasendiri.kasirmudahkmp.ui.view.popup.ActionPopup
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.bookmark
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.delete
import kasirmudah_kmp.shared.generated.resources.ic_arrow_left_32
import kasirmudah_kmp.shared.generated.resources.ic_bookmark_22
import kasirmudah_kmp.shared.generated.resources.ic_delete_22
import kasirmudah_kmp.shared.generated.resources.ic_more_40
import kasirmudah_kmp.shared.generated.resources.receipt_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptTopBar(
    modifier: Modifier = Modifier,
    showMenuIcon: Boolean = false,
    isBookmarked: Boolean = false,
    onUpdateBookmark: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onNavigateBack: () -> Unit
) {

    var showActionPopup by remember { mutableStateOf(false) }

    Row(modifier.fillMaxWidth()
        .background(Primary)
        .statusBarsPadding()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.clip(CircleShape)
                .clickable{
                onNavigateBack.invoke()
            },
            painter = painterResource(Res.drawable.ic_arrow_left_32),
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(Res.string.receipt_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        if(showMenuIcon){
            Image(
                modifier = Modifier.size(32.dp)
                    .clip(CircleShape).clickable{ 
                        showActionPopup = true
                    },
                painter = painterResource(Res.drawable.ic_more_40),
                contentDescription = null
            )
        }else{
            Spacer(Modifier.width(32.dp))
        }

        if(showActionPopup){
            ActionPopup(
                firsItem = Pair(
                    painterResource(Res.drawable.ic_bookmark_22),
                    stringResource(if(isBookmarked) Res.string.cancel else Res.string.bookmark)
                ),
                secondItem = Pair(
                    painterResource(Res.drawable.ic_delete_22),
                    stringResource(Res.string.delete)
                ),
                alignment = Alignment.BottomEnd,
                offset = IntOffset(0, 150),
                properties = PopupProperties(focusable = true),
                onDismiss = {
                    showActionPopup = false
                },
                onFirstItemClick = {
                    onUpdateBookmark?.invoke()
                },
                onSecondItemClick = {
                    onDelete?.invoke()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPrev() {
    ReceiptTopBar(
        onUpdateBookmark = {},
        onDelete = {},
        showMenuIcon = true
    ){}
}