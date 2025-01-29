package com.app.motus_finance.View.NavBottoms.HomeScreen.AddPayments

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.app.motus_finance.View.UtilsComposable.ArrowBack


@Composable
fun AddPayments(navController: NavController) {
    Box(){
        ArrowBack(navController)
    }
}