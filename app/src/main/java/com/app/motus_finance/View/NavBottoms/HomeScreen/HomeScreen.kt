package com.app.motus_finance.View.NavBottoms.HomeScreen

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
import androidx.compose.ui.unit.dp
import com.app.motus_finance.ViewModel.BanksViewModel

@Composable
fun MainScreen(bankViewModel: BanksViewModel) {
 val banks by bankViewModel.getAllBanks().observeAsState(emptyList())
 Column(
  modifier = Modifier
   .fillMaxSize()
   .padding(top = 60.dp)
   .padding(horizontal = 16.dp),
  horizontalAlignment = Alignment.CenterHorizontally
 ) {
  LazyColumn(
   modifier = Modifier.fillMaxWidth()
  ) {
   items(banks) { bank ->
    ListBanks(bank, bankViewModel)
   }
  }
 }
}

