package com.cobasendiri.kasirmudahkmp.ui.features.tab.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudahkmp.ui.view.inputfield.InputField
import com.cobasendiri.kasirmudahkmp.core.domain.model.Product
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.theme.KasirMudahTheme
import com.cobasendiri.kasirmudahkmp.theme.OnPrimary
import com.cobasendiri.kasirmudahkmp.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudahkmp.theme.Primary
import com.cobasendiri.kasirmudahkmp.theme.Surface
import com.cobasendiri.kasirmudahkmp.theme.Tertiary
import com.cobasendiri.kasirmudahkmp.theme.TertiaryVariant
import com.cobasendiri.kasirmudahkmp.theme.White
import com.cobasendiri.kasirmudahkmp.ui.features.tab.profile.ProfileEvent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.component.ShopTabComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.view.FloatingAction
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.view.ProductItem
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.view.TotalItem
import com.cobasendiri.kasirmudahkmp.ui.utils.dateFormat
import com.cobasendiri.kasirmudahkmp.ui.view.FilterChip
import com.cobasendiri.kasirmudahkmp.ui.view.alertbar.UiMessageBar
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.InformationConfirmDialog
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.NegativeConfirmDialog
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.ProductDetailDialog
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.all
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.cart
import kasirmudah_kmp.shared.generated.resources.cart_empty_subtitle
import kasirmudah_kmp.shared.generated.resources.cart_empty_title
import kasirmudah_kmp.shared.generated.resources.close
import kasirmudah_kmp.shared.generated.resources.delete
import kasirmudah_kmp.shared.generated.resources.delete_shop_body
import kasirmudah_kmp.shared.generated.resources.delete_shop_title
import kasirmudah_kmp.shared.generated.resources.ic_kasirmudah
import kasirmudah_kmp.shared.generated.resources.ic_menu
import kasirmudah_kmp.shared.generated.resources.ic_plus_36
import kasirmudah_kmp.shared.generated.resources.menu_setting
import kasirmudah_kmp.shared.generated.resources.products_empty_subtitle
import kasirmudah_kmp.shared.generated.resources.products_empty_title
import kasirmudah_kmp.shared.generated.resources.search
import kasirmudah_kmp.shared.generated.resources.unavailable_body
import kasirmudah_kmp.shared.generated.resources.unavailable_title
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ShopTabScreen(
    innerPadding: PaddingValues,
    component: ShopTabComponent
){
    val state by component.state.subscribeAsState()

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L.milliseconds)
            component.uiMessageShown()
        }
    }

    ShopContent(
        innerPadding = innerPadding,
        state = state,
    ) { event ->
        when (event) {
            is ShopEvent.OnIncreaseProduct -> {
                component.incrementProduct(event.itemId)
            }
            is ShopEvent.OnDecreaseProduct -> {
                component.decrementProduct(event.itemId)
            }
            is ShopEvent.OnUpdateProductColor -> {
                component.updateProductColorCode(
                    event.productId,
                    event.newColor
                )
            }
            is ShopEvent.OnReset -> {
                component.clearCart()
            }
            is ShopEvent.OnFilterChange -> {
                component.updateFilter(event.newFilter)
            }
            is ShopEvent.OnFinish -> {
                component.onNavigateToReceiptDraft()
            }
            is ShopEvent.OnSearch -> {
                component.updateQuery(event.searchQuery)
            }
            is ShopEvent.OnShowAddProductDialog -> {
                component.showAddProductDialog()
            }
            is ShopEvent.OnShowEditProductDialog -> {
                component.showEditProductDialog(event.product)
            }
            is ShopEvent.OnDismissProductDetailDialog -> {
                component.dismissProductDetailDialog()
            }
            is ShopEvent.OnNewProduct -> {
                component.addNewProduct(event.newProduct)
            }
            is ShopEvent.OnUpdateProduct -> {
                component.updateProduct(event.updatedProduct)
            }
            is ShopEvent.OnShowConfirmDeleteDialog -> {
                component.showConfirmDeleteDialog(event.itemId)
            }
            is ShopEvent.OnDismissConfirmDeleteDialog -> {
                component.dismissConfirmDeleteDialog()
            }
            is ShopEvent.OnDeleteProduct -> {
                component.deleteProduct(event.productId)
            }
            is ShopEvent.OnShowUnavailableDialog ->{
                component.showUnavailableDialog()
            }
            is ShopEvent.OnDismissUnavailableDialog ->{
                component.dismissUnavailableDialog()
            }
        }
    }
}

@Composable
fun ShopContent(
    innerPadding: PaddingValues,
    state: ShopTabComponent.ShopTabState,
    event: (ShopEvent) -> Unit
){

    val listState = rememberLazyListState()
    val topPadding = remember(innerPadding){
        innerPadding.calculateTopPadding()
    }

    val topColorAlpha by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val firstItem = layoutInfo.visibleItemsInfo.firstOrNull { it.index == 0 }
            if (firstItem != null) {
                (listState.firstVisibleItemScrollOffset.toFloat() / firstItem.size).coerceIn(0f, 1f)
            } else {
                1f
            }
        }
    }

    val formattedDate = remember(state.date) {
        state.date.dateFormat(shortFormat = true)
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
            item {
                Spacer(Modifier.height(topPadding+16.dp))
                Row(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(180.dp,25.dp),
                        painter = painterResource(Res.drawable.ic_kasirmudah),
                        contentDescription = null,
                        alignment = Alignment.CenterStart
                    )
                    Row(Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(OnPrimary)
                        .clickable{
                            event.invoke(ShopEvent.OnShowUnavailableDialog)
                        }
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.menu_setting),
                            style = MaterialTheme.typography.bodyMedium
                                .copy(color = White)
                        )
                        Spacer(Modifier.width(4.dp))
                        Image(
                            modifier = Modifier.size(19.dp),
                            painter = painterResource(Res.drawable.ic_menu),
                            contentDescription = null
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                Column(Modifier.padding(horizontal = 16.dp)){
                    Text(
                        text = state.shopName,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            stickyHeader {
                Column(Modifier.drawBehind{
                    val roundedRadius = 24.dp.toPx()
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(0f, 0f),
                                    size = Size(size.width, 245.dp.toPx())
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
                }.padding(horizontal = 16.dp)){
                    Spacer(Modifier.height(24.dp))
                    TotalItem(totalAmount = state.totalAmount) {
                        event.invoke(ShopEvent.OnFinish)
                    }
                    Spacer(Modifier.height(14.dp))
                    Row(Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                    ) {
                        InputField(
                            Modifier.weight(4f),
                            stringResource(Res.string.search),
                            value = state.searchQuery,
                            background = White,
                            maxCharacter = 22,
                            showTopLabel = false
                        ) { searchQuery ->
                            event.invoke(ShopEvent.OnSearch(searchQuery))
                        }
                        Spacer(Modifier.width(10.dp))
                        Box(Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(OnPrimaryVariant)
                            .clickable(
                                onClick = {
                                    event.invoke(ShopEvent.OnShowAddProductDialog)
                                },
                            )
                            .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Image(
                                alignment = Alignment.Center,
                                painter = painterResource(Res.drawable.ic_plus_36),
                                contentDescription = null
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            text = stringResource(Res.string.all),
                            filter = ShopFilter.FILTER_ALL,
                            isSelected = state.filter == ShopFilter.FILTER_ALL
                        ) {
                            event.invoke(
                                ShopEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(Res.string.cart),
                            filter = ShopFilter.FILTER_CART,
                            isSelected = state.filter == ShopFilter.FILTER_CART
                        ) {
                            event.invoke(
                                ShopEvent.OnFilterChange(it)
                            )
                        }
                    }
                }
            }
            if(state.showEmptyListView){
                item{
                    Column(Modifier.height(availableHeight*0.4f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val titleRes = if(state.filter == ShopFilter.FILTER_ALL) {
                            Res.string.products_empty_title
                        }else{
                            Res.string.cart_empty_title
                        }
                        val subTitleRes = if(state.filter == ShopFilter.FILTER_ALL) {
                            Res.string.products_empty_subtitle
                        }else{
                            Res.string.cart_empty_subtitle
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
                items = state.shopItemList,
                key = { shopItemState -> shopItemState.product.id}
            ) { item ->
                Spacer(Modifier.height(16.dp))
                ProductItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    state = item,
                    onEdit = {
                        event.invoke(
                            ShopEvent.OnShowEditProductDialog(it)
                        )
                    },
                    onDelete = {
                        event.invoke(
                            ShopEvent.OnShowConfirmDeleteDialog(it)
                        )
                    },
                    onItemIncrease = {
                        ShopEvent.OnIncreaseProduct(
                            itemId = item.product.id
                        ).let { event.invoke(it) }
                    },
                    onItemDecrease = {
                        ShopEvent.OnDecreaseProduct(
                            itemId = item.product.id
                        ).let { event.invoke(it) }
                    },
                    onColorCodeUpdate = { newColor ->
                        ShopEvent.OnUpdateProductColor(
                            productId = item.product.id,
                            newColor = newColor
                        ).let { event.invoke(it) }
                    },
                )
            }
            item {
                val bottomBarSize = innerPadding.calculateBottomPadding() + 12.dp
                val floatingActionSize = if(state.isFloatingActionVisible) 56.dp else 0.dp
                Spacer(Modifier.height(bottomBarSize + floatingActionSize))
            }
        }

        if(state.isFloatingActionVisible){
            FloatingAction(
                Modifier.padding(bottom = innerPadding.calculateBottomPadding()+12.dp)
                    .align(Alignment.BottomCenter),
                onDelete = {
                    event.invoke(ShopEvent.OnReset)
                },
                onDone = {
                    event.invoke(ShopEvent.OnFinish)
                }
            )
        }
        if(state.showAddProductDialog){
            val dismissItemDialogEvent = ShopEvent.OnDismissProductDetailDialog
            ProductDetailDialog(
                onDismiss = {
                    event.invoke(dismissItemDialogEvent)
                },
                onCancel = {
                    event.invoke(dismissItemDialogEvent)
                },
                onSave = {
                    event.invoke(ShopEvent.OnNewProduct(it))
                }
            )
        }
        if(state.showEditProductDialog != null){
            val dismissItemDialogEvent = ShopEvent.OnDismissProductDetailDialog
            ProductDetailDialog(
                product = state.showEditProductDialog,
                onDismiss = {
                    event.invoke(dismissItemDialogEvent)
                },
                onCancel = {
                    event.invoke(dismissItemDialogEvent)
                },
                onSave = {
                    event.invoke(ShopEvent.OnUpdateProduct(it))
                }
            )
        }
        if(state.showConfirmDeleteDialog != null){
            val dismissEvent = ShopEvent.OnDismissConfirmDeleteDialog
            val itemId = state.showConfirmDeleteDialog
            NegativeConfirmDialog(
                title = stringResource(Res.string.delete_shop_title),
                body = stringResource(Res.string.delete_shop_body),
                cancelButton = stringResource(Res.string.cancel),
                confirmButton = stringResource(Res.string.delete),
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onConfirm = {
                    event.invoke(ShopEvent.OnDeleteProduct(itemId))
                }
            )
        }
        if(state.showUnavailableDialog){
            val dismissUnavailableDialog = ShopEvent.OnDismissUnavailableDialog
            InformationConfirmDialog(
                title = stringResource(Res.string.unavailable_title),
                body = stringResource(Res.string.unavailable_body),
                confirmButton = stringResource(Res.string.close),
                onDismiss = { event.invoke(dismissUnavailableDialog) },
                onConfirm = { event.invoke(dismissUnavailableDialog) }
            )
        }

        AlertBarAnimatedVisibility(state.uiMessage != null) {
            state.uiMessage?.let {
                UiMessageBar(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopContentPreview(){
    KasirMudahTheme {
        ShopContent(
            innerPadding = PaddingValues(bottom = 60.dp),
            state = ShopTabComponent.ShopTabState(
                shopName = "Toko Madura A",
                date = 0L,
                totalAmount = 1575000L,
                shopItemList = generateDummyShopItemList()
                    .map {
                        ProductInfo(
                            product = it,
                            count = 0
                        )
                    },
                isFloatingActionVisible = true,
                filter = ShopFilter.FILTER_ALL,
                showEditProductDialog = null,
                showAddProductDialog = false,
                showConfirmDeleteDialog = null
            ),
        ) {}
    }
}


fun generateDummyShopItemList() : MutableList<Product>{
    return MutableList(20){
        Product(
            it.toString(),
            "Nama item",
            15000,
            Tertiary.value.toLong()
        )
    }
}