package com.app.motus_finance.View.NavBottoms.HomeScreen.AddExpenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus_finance.View.UtilsComposable.ArrowBack
import com.app.motus_finance.View.UtilsComposable.ButtonBottomScreen
import com.app.motus_finance.ViewModel.ExpensesViewModel
import com.app.motus_finance.ViewModel.PaymentsViewModel

@Composable
fun AddMarketOrSpent(navController: NavController,
                     paymentsViewModel: PaymentsViewModel,
                     expensesViewModel: ExpensesViewModel) {
    Box(

    ){
        ArrowBack(navController)
        MultiSelectChips(paymentsViewModel)

        if(paymentsViewModel.selectedItem == "Supermercado"){
            SupermarketComposable(modifier = Modifier.align(Alignment.Center), expensesViewModel = expensesViewModel)
        }else if(paymentsViewModel.selectedItem == "Compra"){
            ShoppingComposable()
        }
         ButtonBottomScreen(if(paymentsViewModel.selectedItem == "Supermercado") "Adicionar mercado" else "Adicionar compra",
             onClick = {


         }, modifier = Modifier
             .align(Alignment.BottomCenter))

    }
}