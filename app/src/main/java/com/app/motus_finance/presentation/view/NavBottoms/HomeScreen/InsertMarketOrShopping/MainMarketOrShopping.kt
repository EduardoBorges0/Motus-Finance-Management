package com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertMarketOrShopping

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.app.motus_finance.presentation.view.UtilsComposable.ArrowBack
import com.app.motus_finance.presentation.view.UtilsComposable.ButtonBottomScreen
import com.app.motus_finance.presentation.viewmodel.ExpensesViewModel
import com.app.motus_finance.presentation.viewmodel.PaymentsViewModel

@Composable
fun MainMarketOrShopping(navController: NavController,
                         paymentsViewModel: PaymentsViewModel,
                         expensesViewModel: ExpensesViewModel
) {
    Box(

    ){
        ArrowBack(navController)
        MarketOrShopping(paymentsViewModel)

        if(paymentsViewModel.selectedItem == "Supermercado"){
            SupermarketComposable(modifier = Modifier.align(Alignment.Center), expensesViewModel = expensesViewModel)
        }else if(paymentsViewModel.selectedItem == "Compra"){
            ShoppingComposable()
        }
         ButtonBottomScreen(if(paymentsViewModel.selectedItem == "Supermercado") "Adicionar mercado" else "Adicionar compra",
             onClick = {


         }, modifier = Modifier
             .align(Alignment.BottomCenter),
             )

    }
}