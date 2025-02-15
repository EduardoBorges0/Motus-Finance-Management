package com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertPayment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.motus_finance.presentation.UtilityClass.UtilityClass
import com.app.motus_finance.presentation.view.UtilsComposable.ArrowBack
import com.app.motus_finance.presentation.view.UtilsComposable.ButtonBottomScreen
import com.app.motus_finance.presentation.viewmodel.PaymentsViewModel


@Composable
fun InsertPayment(navController: NavController, paymentsViewModel: PaymentsViewModel) {
    var inputText by remember { mutableStateOf("0.00") }

    Box (modifier = Modifier.fillMaxSize()) {
        ArrowBack(navController)
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = inputText,
                onValueChange = {
                    inputText = UtilityClass.formatToCurrency(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                textStyle = TextStyle(color = Color.Black, textAlign = TextAlign.Center, fontSize = 50.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
        ButtonBottomScreen(
            buttonText = "Adicionar pagamento",
            onClick = {
                val sanitizedValue = inputText.replace(".", "").replace(",", ".") // Remove pontos e ajusta para um formato válido
                val numericValue = sanitizedValue.toDoubleOrNull() ?: 0.0
                paymentsViewModel.insertPayments(numericValue)

                navController.popBackStack()            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


