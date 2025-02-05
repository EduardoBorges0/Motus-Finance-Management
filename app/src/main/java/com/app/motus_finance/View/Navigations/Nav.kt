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
import com.app.motus_finance.Models.Repositories.RepositoriesMarket
import com.app.motus_finance.Models.Repositories.RepositoriesDueDates
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses
import com.app.motus_finance.Models.Repositories.RepositoriesGraphics
import com.app.motus_finance.Models.Repositories.RepositoriesPayments
import com.app.motus_finance.Models.RoomConfig.DatabaseProvider
import com.app.motus_finance.Service.MarketService
import com.app.motus_finance.Service.ExpensesService
import com.app.motus_finance.Service.GraphicsService
import com.app.motus_finance.Service.PaymentsService
import com.app.motus_finance.View.NavBottoms.HomeScreen.InsertMarketOrShopping.MainMarketOrShopping
import com.app.motus_finance.View.NavBottoms.HomeScreen.InsertPayment.InsertPayment
import com.app.motus_finance.View.ui.theme.Motus_FINANCETheme
import com.app.motus_finance.ViewModel.MarketViewModel
import com.app.motus_finance.ViewModel.ExpensesViewModel
import com.app.motus_finance.ViewModel.GraphicsViewModel
import com.app.motus_finance.ViewModel.PaymentsViewModel

class Nav : ComponentActivity() {
    private lateinit var banksViewModel: MarketViewModel
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



        banksViewModel = MarketViewModel(MarketService(RepositoriesMarket(bankDAO)))
        expensesViewModel = ExpensesViewModel(ExpensesService(RepositoriesExpenses(expenseDAO), RepositoriesMarket(bankDAO)))
        paymentsViewModel = PaymentsViewModel(PaymentsService(RepositoriesPayments(paymentsDAO)))
        graphicsViewModel = GraphicsViewModel(GraphicsService(RepositoriesGraphics(graphicsDAO), RepositoriesDueDates(DueDatesDAO), RepositoriesExpenses(expenseDAO), RepositoriesMarket(bankDAO)))
        setContent {
            LaunchedEffect(Unit) {
 }
            Motus_FINANCETheme {
              SetupNavController(banksViewModel, paymentsViewModel, graphicsViewModel, expensesViewModel)
            }
        }
    }
}

@Composable
fun SetupNavController(
    banksViewModel: MarketViewModel,
    paymentsViewModel: PaymentsViewModel,
    graphicsViewModel: GraphicsViewModel, expensesViewModel: ExpensesViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main") {
            NavigationBarComposable(
            banksViewModel,
            paymentsViewModel, navController
            )
        }
        composable("insertPayment") {
            InsertPayment(navController, paymentsViewModel)
        }
        composable("marketOrShopping") {
            MainMarketOrShopping(navController, paymentsViewModel, expensesViewModel)
        }
    }
}