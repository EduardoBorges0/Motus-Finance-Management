package com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertMarketOrShopping

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.motus_finance.presentation.view.UtilsComposable.DatePickerComposable
import com.app.motus_finance.presentation.viewmodel.ExpensesViewModel

@Composable
fun SupermarketComposable(modifier: Modifier, expensesViewModel: ExpensesViewModel){
    var marketName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        TextField(
            value = marketName,
            onValueChange = { marketName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp, vertical = 20.dp)
                .border(border = BorderStroke(0.5.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            label = { Text("Nome do mercado") }
            )
      DatePickerComposable(onDateSelected = {
          newDate -> expensesViewModel.selectedDate = newDate
      },
          selectedDate = expensesViewModel.selectedDate)
    }
}