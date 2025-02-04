package com.app.motus_finance.View.NavBottoms.HomeScreen.AddExpenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.app.motus_finance.View.UtilsComposable.ArrowBack

@Composable
fun AddMarketOrSpent(navController: NavController) {
    Box(

    ){
        ArrowBack(navController)
        MultiSelectChips()
    }
}