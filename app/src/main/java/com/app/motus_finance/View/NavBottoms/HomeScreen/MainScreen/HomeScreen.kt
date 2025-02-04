package com.app.motus_finance.View.NavBottoms.HomeScreen.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus_finance.ViewModel.BanksViewModel
import com.app.motus_finance.ViewModel.PaymentsViewModel

@Composable
fun MainScreen(bankViewModel: BanksViewModel, paymentsViewModel: PaymentsViewModel, navController: NavController) {
 val banks by bankViewModel.getAllBanks().observeAsState(emptyList())
 Column(
  modifier = Modifier
   .fillMaxSize()
   .background(Color.White)
   .padding(top = 30.dp)
   .padding(horizontal = 16.dp),
  horizontalAlignment = Alignment.CenterHorizontally
 ) {
  PaymentBox(modifier = Modifier.align(Alignment.Start), paymentsViewModel, navController)

  LazyColumn(
   modifier = Modifier.fillMaxWidth()
  ) {
   items(banks) { bank ->
    ListBanks(bank, bankViewModel)
   }
  }
 }
}

