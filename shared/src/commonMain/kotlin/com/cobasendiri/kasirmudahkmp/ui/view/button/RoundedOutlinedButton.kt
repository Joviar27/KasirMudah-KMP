package com.cobasendiri.kasirmudahkmp.ui.view.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudahkmp.theme.White

@Composable
fun RoundedOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
){
    RoundedButton(
        modifier = modifier,
        text = text,
        borderColor = OnPrimaryVariant,
        isEnabled = isEnabled,
        onClick = onClick
    )
}


@Preview
@Composable
fun RoundedOutlinedButtonPrev() {
    Column(Modifier.width(180.dp)
        .background(White)
        .padding(16.dp)
    ) {
        RoundedOutlinedButton(
            text = "Outlined"
        ){

        }
    }
}