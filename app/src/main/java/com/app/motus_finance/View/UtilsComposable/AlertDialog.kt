package com.app.motus_finance.View.UtilsComposable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AlertDialogComposable(showDialog: Boolean,
                          onDismiss: () -> Unit,
                          onConfirm: () -> Unit) {
    if(showDialog){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Atenção!") },
            text = { Text("Você tem certeza que deseja continuar?") },
            confirmButton = {
                Button(onClick = onConfirm ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }

}