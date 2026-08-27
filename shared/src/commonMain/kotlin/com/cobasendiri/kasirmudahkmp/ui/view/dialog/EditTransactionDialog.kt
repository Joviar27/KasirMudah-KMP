package com.cobasendiri.kasirmudahkmp.ui.view.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudahkmp.theme.White
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudahkmp.ui.view.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudahkmp.ui.view.inputfield.InputField
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.cancel
import kasirmudah_kmp.shared.generated.resources.edit_transaction
import kasirmudah_kmp.shared.generated.resources.save
import kasirmudah_kmp.shared.generated.resources.transaction_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditTransactionDialog(
    name: String,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onSave: (String) -> Unit
) {

    var nameDraft by remember { mutableStateOf(name) }

    val isSaveButtonEnabled = remember(nameDraft){
        nameDraft.let {
            it.isNotBlank() && it.firstOrNull()?.isWhitespace() == false
        }
    }

    BaseDialog(onDismiss = onDismiss) {
        Column(Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.edit_transaction),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(24.dp))
            InputField(
                label = stringResource(Res.string.transaction_name),
                value = nameDraft,
            ) {
                nameDraft = it
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
                    isEnabled = isSaveButtonEnabled
                ) {
                    onSave.invoke(nameDraft)
                }
            }
        }
    }
}

@Preview
@Composable
fun EditTransactionDialogPrev(){
    EditTransactionDialog("Tes Nama",{},{},{})
}