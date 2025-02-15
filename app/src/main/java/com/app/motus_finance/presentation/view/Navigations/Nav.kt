package com.app.motus_finance.presentation.view.Navigations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertMarketOrShopping.InsertMarket.InsertMarket
import com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertMarketOrShopping.MainMarketOrShopping
import com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertPayment.InsertPayment
import com.app.motus_finance.presentation.view.ui.theme.Motus_FINANCETheme
import com.app.motus_finance.presentation.viewmodel.MarketViewModel
import com.app.motus_finance.presentation.viewmodel.ExpensesViewModel
import com.app.motus_finance.presentation.viewmodel.GraphicsViewModel
import com.app.motus_finance.presentation.viewmodel.PaymentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Nav : ComponentActivity() {
    private val marketViewModel: MarketViewModel by viewModels()
    private val expensesViewModel: ExpensesViewModel by viewModels()
    private val paymentsViewModel: PaymentsViewModel by viewModels()
    private val graphicsViewModel: GraphicsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Motus_FINANCETheme {
              SetupNavController(marketViewModel, paymentsViewModel, graphicsViewModel, expensesViewModel)
            }
        }
    }
}

@Composable
fun SetupNavController(
    marketViewModel: MarketViewModel,
    paymentsViewModel: PaymentsViewModel,
    graphicsViewModel: GraphicsViewModel, expensesViewModel: ExpensesViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main") {
            NavigationBarComposable(
            marketViewModel,
            paymentsViewModel, navController
            )
        }
        composable("insertPayment") {
            InsertPayment(navController, paymentsViewModel)
        }
        composable("marketOrShopping") {
            MainMarketOrShopping(navController, paymentsViewModel, marketViewModel = marketViewModel)
        }
        composable("insertMarket/{name}"){
            val name = it.arguments?.getString("name")
            InsertMarket(name, marketViewModel, navController)
        }
    }
}