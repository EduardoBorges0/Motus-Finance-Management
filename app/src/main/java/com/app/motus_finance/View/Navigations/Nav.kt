package com.app.motus_finance.View.Navigations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.RoomConfig.DatabaseProvider
import com.app.motus_finance.Service.BankService
import com.app.motus_finance.View.NavBottoms.HomeScreen.MainScreen
import com.app.motus_finance.View.Navigations.ui.theme.Motus_FINANCETheme
import com.app.motus_finance.ViewModel.BanksViewModel

class Nav : ComponentActivity() {
    private lateinit var banksViewModel: BanksViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bankDAO = DatabaseProvider.getBankDAO(this)
        banksViewModel = BanksViewModel(BankService(RepositoriesBank(bankDAO)))
        setContent {
            LaunchedEffect(Unit) {

            }
            Motus_FINANCETheme {
              SetupNavController(banksViewModel)
            }
        }
    }
}

@Composable
fun SetupNavController(banksViewModel: BanksViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main") { MainScreen(banksViewModel) }
    }
}