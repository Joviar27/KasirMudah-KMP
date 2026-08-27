package com.cobasendiri.kasirmudahkmp.ui.features.tab.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.theme.KasirMudahTheme
import com.cobasendiri.kasirmudahkmp.theme.Primary
import com.cobasendiri.kasirmudahkmp.theme.Surface
import com.cobasendiri.kasirmudahkmp.theme.White
import com.cobasendiri.kasirmudahkmp.ui.features.tab.profile.component.ProfileTabComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.profile.view.ProfileMenuItem
import com.cobasendiri.kasirmudahkmp.ui.view.alertbar.UiMessageBar
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.EditProfileDialog
import com.cobasendiri.kasirmudahkmp.ui.view.dialog.InformationConfirmDialog
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.close
import kasirmudah_kmp.shared.generated.resources.default_shop_name
import kasirmudah_kmp.shared.generated.resources.ic_about_us_24
import kasirmudah_kmp.shared.generated.resources.ic_cloud_upload_24
import kasirmudah_kmp.shared.generated.resources.ic_kasirmudah
import kasirmudah_kmp.shared.generated.resources.ic_person_padded
import kasirmudah_kmp.shared.generated.resources.ic_profile
import kasirmudah_kmp.shared.generated.resources.ic_settings_24
import kasirmudah_kmp.shared.generated.resources.menu_about_us
import kasirmudah_kmp.shared.generated.resources.menu_edit_profile
import kasirmudah_kmp.shared.generated.resources.menu_setting
import kasirmudah_kmp.shared.generated.resources.menu_sync_data
import kasirmudah_kmp.shared.generated.resources.unavailable_body
import kasirmudah_kmp.shared.generated.resources.unavailable_title
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ProfileTabScreen(
    innerPadding: PaddingValues,
    component: ProfileTabComponent
){
    val state by component.state.subscribeAsState()

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L.milliseconds)
            component.uiMessageShown()
        }
    }

    ProfileScreenContent(
        innerPadding,
        state
    ){ event ->
        when(event){
            is ProfileEvent.OnShowEditProfileDialog ->{
                component.showEditProfileDialog(event.shopProfile)
            }
            is ProfileEvent.OnDismissEditProfileDialog ->{
                component.dismissEditProfileDialog()
            }
            is ProfileEvent.OnShowUnavailableDialog ->{
                component.showUnavailableDialog()
            }
            is ProfileEvent.OnDismissUnavailableDialog ->{
                component.dismissUnavailableDialog()
            }
            is ProfileEvent.OnEditProfile ->{
                component.saveShopProfile(event.newShopProfile)
            }
        }
    }
}

@Composable
fun ProfileScreenContent(
    innerPadding: PaddingValues,
    state: ProfileTabComponent.ProfileState,
    event: (ProfileEvent) -> Unit
){
    val topPadding = remember(innerPadding){
        innerPadding.calculateTopPadding()
    }

    Box(Modifier
        .fillMaxSize()
        .background(Surface)
    ) {
        //Top decoration view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topPadding + 250.dp)
                .drawBehind {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Primary, Color.Transparent)
                        ),
                        size = Size(size.width, (topPadding + 250.dp).toPx())
                    )
                }
        )

        Column(Modifier.fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(topPadding))
            Image(
                modifier = Modifier.size(180.dp,25.dp),
                painter = painterResource(Res.drawable.ic_kasirmudah),
                contentDescription = null,
                alignment = Alignment.CenterStart
            )
            Spacer(Modifier.height(24.dp))
            AsyncImage(
                modifier = Modifier.size(130.dp)
                    .clip(CircleShape)
                    .background(White),
                model = state.shopProfile.shopImage?.bytes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                fallback = painterResource(Res.drawable.ic_person_padded),
                error = painterResource(Res.drawable.ic_person_padded)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = state.shopProfile.shopName.takeIf { it.isNotBlank() }
                    ?: stringResource(Res.string.default_shop_name),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(24.dp))

            ProfileMenuItem(
                startIcon = painterResource(Res.drawable.ic_profile),
                name = stringResource(Res.string.menu_edit_profile)
            ) {
                event.invoke(ProfileEvent.OnShowEditProfileDialog(state.shopProfile))
            }
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(
                startIcon = painterResource(Res.drawable.ic_about_us_24),
                name = stringResource(Res.string.menu_about_us)
            ) {
                event.invoke(ProfileEvent.OnShowUnavailableDialog)
            }
            Spacer(Modifier.height(16.dp))
            ProfileMenuItem(
                startIcon = painterResource(Res.drawable.ic_settings_24),
                name = stringResource(Res.string.menu_setting)
            ) {
                event.invoke(ProfileEvent.OnShowUnavailableDialog)
            }
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(
                startIcon = painterResource(Res.drawable.ic_cloud_upload_24),
                name = stringResource(Res.string.menu_sync_data)
            ) {
                event.invoke(ProfileEvent.OnShowUnavailableDialog)
            }
        }
        if(state.showEditProfileDialog != null){
            val dismissEditDialogEvent = ProfileEvent.OnDismissEditProfileDialog
            EditProfileDialog(
                state.showEditProfileDialog,
                onDismiss = { event.invoke(dismissEditDialogEvent) },
                onCancel = { event.invoke(dismissEditDialogEvent) },
                onSave = {
                    event.invoke(ProfileEvent.OnEditProfile(it))
                }
            )
        }
        if(state.showUnavailableDialog){
            val dismissUnavailableDialog = ProfileEvent.OnDismissUnavailableDialog
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
fun ProfileScreenPrev(){
    KasirMudahTheme {
        ProfileScreenContent(
            innerPadding = PaddingValues(bottom = 60.dp),
            state = ProfileTabComponent.ProfileState(
                ShopProfile(
                    "Toko Madura A",
                    null
                ),
                showEditProfileDialog = null,
                showUnavailableDialog = false
            )
        ){}
    }
}