package com.app.motus_finance.View.UtilsComposable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonBottomScreen(buttonText: String, onClick: (String?) -> Unit, modifier: Modifier) {
    Button(onClick = {
        onClick(null)
    },
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(buttonText)
    }
}