package com.app.motus_finance.View.NavBottoms.HomeScreen.AddPayments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.motus_finance.UtilityClass.DateUtils
import com.app.motus_finance.View.UtilsComposable.ArrowBack


@Composable
fun AddPayments(navController: NavController) {
    var inputText by remember { mutableStateOf("0,00") }

    Box(modifier = Modifier.fillMaxSize()) {
        ArrowBack(navController)
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = inputText,
                fontSize = 60.sp,
                modifier = Modifier
                    .clickable {
                        inputText = addNumber(inputText, "5")
                    }
            )
        }
    }
}

fun addNumber(currentText: String, newDigit: String): String {
    val cleanText = currentText
        .replace(",", "")
        .replace(".", "")

    // Se for "000", remover zeros à esquerda
    val newText = if (cleanText == "000") newDigit else cleanText + newDigit

    // Garantir que temos sempre dois dígitos após a vírgula
    val formatted = newText.padStart(3, '0')



    val integerPart = formatted.dropLast(2)
    val integerPartTwo = integerPart.drop(2)
    val decimalPart = formatted.takeLast(2)
    val total = "${if(newText.length > 5) integerPartTwo else integerPart},$decimalPart"
    return total
}
