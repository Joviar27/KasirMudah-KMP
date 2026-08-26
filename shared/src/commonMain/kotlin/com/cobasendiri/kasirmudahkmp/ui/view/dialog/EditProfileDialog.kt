package com.cobasendiri.kasirmudahkmp.ui.view.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.theme.OnPrimary
import com.cobasendiri.kasirmudahkmp.theme.Surface
import com.cobasendiri.kasirmudahkmp.theme.White
import com.cobasendiri.kasirmudahkmp.ui.utils.ImageWrapper
import com.cobasendiri.kasirmudahkmp.ui.utils.rememberImagePickerLauncher
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudahkmp.ui.view.inputfield.InputField
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.edit_shop_profile
import kasirmudah_kmp.shared.generated.resources.ic_edit
import kasirmudah_kmp.shared.generated.resources.ic_person_padded
import kasirmudah_kmp.shared.generated.resources.save
import kasirmudah_kmp.shared.generated.resources.shop_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditProfileDialog(
    shopProfile: ShopProfile,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onSave: (ShopProfile) -> Unit
) {

    var shopNameDraft by remember(shopProfile.shopName) {
        mutableStateOf((shopProfile.shopName))
    }

    var shopImageDraft by remember(shopProfile.shopImage) {
        mutableStateOf((shopProfile.shopImage))
    }

    val isNextButtonEnabled = remember(shopNameDraft){
        shopNameDraft.let {
            it.isNotBlank() && it.firstOrNull()?.isWhitespace() == false
        }
    }

    val imageLauncher = rememberImagePickerLauncher(
        onResult = { bytes ->
            bytes?.let {
                shopImageDraft = ImageWrapper(it)
            }
        }
    )

    BaseDialog(
        onDismiss = onDismiss
    ) {
        Column(Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.edit_shop_profile),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center){
                AsyncImage(
                    modifier = Modifier.size(120.dp)
                        .clip(CircleShape)
                        .background(Surface),
                    model = shopImageDraft?.bytes,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    fallback = painterResource(Res.drawable.ic_person_padded),
                    error = painterResource(Res.drawable.ic_person_padded),
                )
                Image(
                    modifier = Modifier.size(34.dp)
                        .clip(CircleShape)
                        .background(OnPrimary)
                        .clickable{
                            imageLauncher.launch()
                        }
                        .padding(8.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(Res.drawable.ic_edit),
                    contentDescription = null
                )
            }
            Spacer(Modifier.height(24.dp))
            InputField(
                label = stringResource(Res.string.shop_name),
                value = shopNameDraft,
                maxCharacter = 35
            ) {
                shopNameDraft = it
            }
            Spacer(Modifier.height(24.dp))
            Row {
                RoundedOutlinedButton(
                    modifier = Modifier.width(140.dp),
                    text = stringResource(Res.string.cancel)
                ){
                    onCancel.invoke()
                }
                Spacer(Modifier.width(8.dp))
                RoundedPrimaryButton(
                    modifier = Modifier.width(140.dp),
                    text = stringResource(Res.string.save),
                    isEnabled = isNextButtonEnabled
                ) {
                    onSave.invoke(ShopProfile(shopNameDraft,shopImageDraft))
                }
            }
        }
    }
}

@Preview
@Composable
fun EditProfileDialogPrev() {
    EditProfileDialog(
        ShopProfile(
            "Toko Madura A",
            null
        ),
        {},{},{}
    )
}