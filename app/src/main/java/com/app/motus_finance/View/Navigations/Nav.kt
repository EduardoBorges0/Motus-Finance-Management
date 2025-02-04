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
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.Entities.DueDates
import com.app.motus_finance.Models.Entities.Payments
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.Repositories.RepositoriesDueDates
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses
import com.app.motus_finance.Models.Repositories.RepositoriesGraphics
import com.app.motus_finance.Models.Repositories.RepositoriesPayments
import com.app.motus_finance.Models.RoomConfig.DatabaseProvider
import com.app.motus_finance.R
import com.app.motus_finance.Service.BankService
import com.app.motus_finance.Service.ExpensesService
import com.app.motus_finance.Service.GraphicsService
import com.app.motus_finance.Service.PaymentsService
import com.app.motus_finance.View.NavBottoms.HomeScreen.AddExpenses.AddMarketOrSpent
import com.app.motus_finance.View.NavBottoms.HomeScreen.AddExpenses.MultiSelectChips
import com.app.motus_finance.View.NavBottoms.HomeScreen.AddPayments.AddPayments
import com.app.motus_finance.View.Navigations.ui.theme.Motus_FINANCETheme
import com.app.motus_finance.ViewModel.BanksViewModel
import com.app.motus_finance.ViewModel.ExpensesViewModel
import com.app.motus_finance.ViewModel.GraphicsViewModel
import com.app.motus_finance.ViewModel.PaymentsViewModel

class Nav : ComponentActivity() {
    private lateinit var banksViewModel: BanksViewModel
    private lateinit var expensesViewModel: ExpensesViewModel
    private lateinit var paymentsViewModel: PaymentsViewModel
    private lateinit var graphicsViewModel: GraphicsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bankDAO = DatabaseProvider.getBankDAO(this)
        val expenseDAO = DatabaseProvider.getExpenseDAO(this)
        val paymentsDAO = DatabaseProvider.getPaymentDAO(this)
        val graphicsDAO = DatabaseProvider.getGraphicsDAO(this)
        val DueDatesDAO = DatabaseProvider.getDueDatesDAO(this)



        banksViewModel = BanksViewModel(BankService(RepositoriesBank(bankDAO)))
        expensesViewModel = ExpensesViewModel(ExpensesService(RepositoriesExpenses(expenseDAO), RepositoriesBank(bankDAO)))
        paymentsViewModel = PaymentsViewModel(PaymentsService(RepositoriesPayments(paymentsDAO)))
        graphicsViewModel = GraphicsViewModel(GraphicsService(RepositoriesGraphics(graphicsDAO), RepositoriesDueDates(DueDatesDAO), RepositoriesExpenses(expenseDAO), RepositoriesBank(bankDAO)))
        setContent {
            LaunchedEffect(Unit) {
 }
            Motus_FINANCETheme {
              SetupNavController(banksViewModel, paymentsViewModel, graphicsViewModel)
            }
        }
    }
}

@Composable
fun SetupNavController(
    banksViewModel: BanksViewModel,
    paymentsViewModel: PaymentsViewModel,
    graphicsViewModel: GraphicsViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main") {
            NavigationBarComposable(
            banksViewModel,
            paymentsViewModel, navController
            )
        }
        composable("addPayments") {
            AddPayments(navController, paymentsViewModel)
        }
        composable("marketOrSpent") {
            AddMarketOrSpent(navController)
        }
    }
}