package com.app.motus_finance.View.UtilsComposable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.motus_finance.View.ui.theme.MainColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AlertDialogComposable(showDialog: Boolean,
                          onDismiss: () -> Unit,
                          onConfirm: () -> Unit,
                          text: String) {
    if(showDialog){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Atenção!") },
            text = { Text(text) },
            confirmButton = {
                Button(onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MainColor
                    ),
                    ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MainColor
                    )) {
                    Text("Cancelar")
                }
            },
            containerColor = Color.White,
            modifier = Modifier.border(BorderStroke(0.8.dp, MainColor), RoundedCornerShape(18.dp))

        )
    }

}