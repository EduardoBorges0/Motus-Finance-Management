package com.app.motus_finance.View.UtilsComposable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithDropdown(options: List<String>, buttonText : String) {
    var expanded by remember { mutableStateOf(false) } // Controla a visibilidade do menu

    Column {
        OutlinedButton (
            onClick = { expanded = true },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(280.dp)
                .height(50.dp)) {
            Text(buttonText)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        println("Selecionado: $option")
                        expanded = false
                    }
                )
            }
        }
    }
}
