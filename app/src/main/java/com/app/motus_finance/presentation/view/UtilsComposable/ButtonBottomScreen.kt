package com.app.motus_finance.presentation.view.UtilsComposable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.motus_finance.presentation.view.ui.theme.MainColor

@Composable
fun ButtonBottomScreen(buttonText: String, onClick: (String?) -> Unit, modifier: Modifier) {
    Button(onClick = {
        onClick(null)
    },
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MainColor,
            contentColor = Color.White
        )
    ) {
        Text(buttonText)
    }
}