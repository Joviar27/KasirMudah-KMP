package com.cobasendiri.kasirmudahkmp.ui.features.tab.history

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudahkmp.theme.OnPrimary
import com.cobasendiri.kasirmudahkmp.theme.Primary
import com.cobasendiri.kasirmudahkmp.theme.Secondary
import com.cobasendiri.kasirmudahkmp.theme.Surface
import com.cobasendiri.kasirmudahkmp.theme.TertiaryVariant
import com.cobasendiri.kasirmudahkmp.ui.features.tab.history.component.TransactionHistoryTabComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.history.view.TransactionHistoryItem
import com.cobasendiri.kasirmudahkmp.ui.view.FilterChip
import com.cobasendiri.kasirmudahkmp.ui.view.alertbar.UiMessageBar
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.EditTransactionDialog
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.NegativeConfirmDialog
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.all
import kasirmudah_kmp.shared.generated.resources.bookmark_empty_subtitle
import kasirmudah_kmp.shared.generated.resources.bookmark_empty_title
import kasirmudah_kmp.shared.generated.resources.bookmarked
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.delete
import kasirmudah_kmp.shared.generated.resources.delete_transaction_body
import kasirmudah_kmp.shared.generated.resources.delete_transaction_title
import kasirmudah_kmp.shared.generated.resources.history
import kasirmudah_kmp.shared.generated.resources.history_empty_subtitle
import kasirmudah_kmp.shared.generated.resources.history_empty_title
import kasirmudah_kmp.shared.generated.resources.last_month
import kasirmudah_kmp.shared.generated.resources.last_week
import kasirmudah_kmp.shared.generated.resources.today
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TransactionHistoryTabScreen(
    innerPadding: PaddingValues,
    component: TransactionHistoryTabComponent
){
    val state by component.state.subscribeAsState()

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L.milliseconds)
            component.uiMessageShown()
        }
    }

    TransactionHistoryContent(
        innerPadding,
        state
    ){ event ->
        when(event){
            is TransactionHistoryEvent.OnUpdateBookmark ->{
                component.updateBookmark(event.transactionId)
            }
            is TransactionHistoryEvent.OnDelete ->{
                component.deleteTransaction(event.transactionId)
            }
            is TransactionHistoryEvent.OnShowConfirmDeleteDialog ->{
                component.showConfirmDeleteDialog(event.transactionId)
            }
            is TransactionHistoryEvent.OnDismissConfirmDeleteDialog -> {
                component.dismissConfirmDeleteDialog()
            }
            is TransactionHistoryEvent.OnFilterChange ->{
                component.updateFilter(event.newFilter)
            }
            is TransactionHistoryEvent.OnNavigateToDetail ->{
                component.onNavigateDetail(event.transactionId)
            }
            is TransactionHistoryEvent.OnShowEditDialog ->{
                component.showEditDialog(event.transactionId, event.name)
            }
            is TransactionHistoryEvent.OnDismissEditDialog ->{
                component.dismissEditDialog()
            }
            is TransactionHistoryEvent.OnUpdateName ->{
                component.updateTransactionName(event.transactionId, event.updatedName)
            }
        }
    }
}

@Composable
fun TransactionHistoryContent(
    innerPadding: PaddingValues,
    state: TransactionHistoryTabComponent.TransactionHistoryTabState,
    event: (TransactionHistoryEvent) -> Unit
){
    val listState = rememberLazyListState()
    val filterScrollState = rememberScrollState()

    val topPadding = remember(innerPadding){
        innerPadding.calculateTopPadding()
    }

    val topColorAlpha by remember {
        derivedStateOf {
            val firstItemIndex = listState.firstVisibleItemIndex
            val scrollOffset = listState.firstVisibleItemScrollOffset

            val transitionThreshold = 100f
            if (firstItemIndex > 0) {
                1f
            } else {
                (scrollOffset / transitionThreshold).coerceIn(0f, 1f)
            }
        }
    }

    BoxWithConstraints(Modifier
        .fillMaxSize()
        .background(Surface)
    ) {
        val availableHeight = maxHeight

        //Top decoration view
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(topPadding + 250.dp)
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Primary, Color.Transparent)
                    ),
                    alpha = 1f - topColorAlpha,
                    size = Size(size.width, (topPadding + 250.dp).toPx())
                )
            }
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState
        ) {
            stickyHeader {
                Column(Modifier.drawBehind{
                    val roundedRadius = 16.dp.toPx()
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(0f, 0f),
                                    size = Size(size.width, 150.dp.toPx())
                                ),
                                topLeft = CornerRadius.Zero,
                                topRight = CornerRadius.Zero,
                                bottomRight = CornerRadius(roundedRadius, roundedRadius),
                                bottomLeft = CornerRadius(roundedRadius, roundedRadius)
                            )
                        )
                    }
                    drawPath(
                        path = path,
                        color = TertiaryVariant,
                        alpha = topColorAlpha,
                    )
                }.fillMaxWidth()) {
                    Spacer(Modifier.height(topPadding+16.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(Res.string.history),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        Modifier.horizontalScroll(filterScrollState),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(Res.string.bookmarked),
                            filter = TransactionFilter.FILTER_BOOKMARKED,
                            isSelected = state.filter == TransactionFilter.FILTER_BOOKMARKED,
                            selectedBgColor = Secondary,
                            selectedTextColor = OnPrimary
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(Res.string.all),
                            filter = TransactionFilter.FILTER_ALL,
                            isSelected = state.filter == TransactionFilter.FILTER_ALL
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(Res.string.today),
                            filter = TransactionFilter.FILTER_TODAY,
                            isSelected = state.filter == TransactionFilter.FILTER_TODAY
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(Res.string.last_week),
                            filter = TransactionFilter.FILTER_LAST_WEEK,
                            isSelected = state.filter == TransactionFilter.FILTER_LAST_WEEK
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            modifier = Modifier.padding(end = 16.dp),
                            text = stringResource(Res.string.last_month),
                            filter = TransactionFilter.FILTER_LAST_MONTH,
                            isSelected = state.filter == TransactionFilter.FILTER_LAST_MONTH
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                    }
                }
            }
            if(state.showEmptyListView){
                item{
                    Column(Modifier.height(availableHeight*0.7f)
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val titleRes = if(state.filter == TransactionFilter.FILTER_BOOKMARKED) {
                            Res.string.bookmark_empty_title
                        }else{
                            Res.string.history_empty_title
                        }
                        val subTitleRes = if(state.filter == TransactionFilter.FILTER_BOOKMARKED) {
                            Res.string.bookmark_empty_subtitle
                        }else{
                            Res.string.history_empty_subtitle
                        }
                        Text(
                            text = stringResource(titleRes),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stringResource(subTitleRes),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            items(
                items = state.transactionList,
                key = { transactionItemState -> transactionItemState.id }
            ) { item ->
                Spacer(Modifier.height(16.dp))
                TransactionHistoryItem(
                    Modifier.padding(horizontal = 16.dp),
                    state = item,
                    onUpdateBookmark = {
                        event.invoke(TransactionHistoryEvent.OnUpdateBookmark(it))
                    },
                    onDelete = {
                        event.invoke(TransactionHistoryEvent.OnShowConfirmDeleteDialog(it))
                    },
                    onItemClick = {
                        event.invoke(TransactionHistoryEvent.OnNavigateToDetail(it))
                    },
                    onEditClick = { transactionId, name ->
                        event.invoke(TransactionHistoryEvent.OnShowEditDialog(transactionId, name))
                    }
                )
            }
            item {
                Spacer(Modifier.height(innerPadding.calculateBottomPadding() + 12.dp))
            }
        }
        if(state.showEditDialog != null){
            val dismissEvent = TransactionHistoryEvent.OnDismissEditDialog
            val transactionId = state.showEditDialog.first
            val name = state.showEditDialog.second
            EditTransactionDialog(
                name = name,
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onSave = { updatedName ->
                    event.invoke(TransactionHistoryEvent.OnUpdateName(transactionId, updatedName))
                }
            )
        }
        if(state.showConfirmDeleteDialog != null){
            val dismissEvent = TransactionHistoryEvent.OnDismissConfirmDeleteDialog
            val itemId = state.showConfirmDeleteDialog
            NegativeConfirmDialog(
                title = stringResource(Res.string.delete_transaction_title),
                body = stringResource(Res.string.delete_transaction_body),
                cancelButton = stringResource(Res.string.cancel),
                confirmButton = stringResource(Res.string.delete),
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onConfirm = {
                    event.invoke(TransactionHistoryEvent.OnDelete(itemId))
                }
            )
        }
        AlertBarAnimatedVisibility(state.uiMessage != null) {
            state.uiMessage?.let {
                UiMessageBar(it)
            }
        }
    }
}