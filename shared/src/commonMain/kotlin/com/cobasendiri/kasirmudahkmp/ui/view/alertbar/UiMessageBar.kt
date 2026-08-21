package com.cobasendiri.kasirmudahkmp.ui.view.alertbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cobasendiri.kasirmudahkmp.theme.Negative
import com.cobasendiri.kasirmudahkmp.theme.Primary
import com.cobasendiri.kasirmudahkmp.theme.Tertiary
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudahkmp.ui.utils.UiMessageUtil.asUiMessage
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.ic_check_24_white
import kasirmudah_kmp.shared.generated.resources.ic_error_24_white
import kasirmudah_kmp.shared.generated.resources.ic_info_outline_24_white
import kasirmudah_kmp.shared.generated.resources.success_update
import org.jetbrains.compose.resources.painterResource

@Composable
fun UiMessageBar(
    uiMessage: UiMessage
) {
    val icon = when(uiMessage.type){
        UiMessageType.SUCCESS -> painterResource(Res.drawable.ic_check_24_white)
        UiMessageType.ERROR -> painterResource(Res.drawable.ic_error_24_white)
        else -> painterResource(Res.drawable.ic_info_outline_24_white)
    }
    val color = when(uiMessage.type){
        UiMessageType.SUCCESS -> Primary
        UiMessageType.ERROR -> Negative
        else -> Tertiary
    }
    InformationBar(
        imageStart = icon,
        imageBackground = color,
        message = uiMessage.asString()
    )
}

@Preview
@Composable
fun UiMessageBarPrev(){
    UiMessageBar(
        Res.string.success_update.asUiMessage()
    )
}