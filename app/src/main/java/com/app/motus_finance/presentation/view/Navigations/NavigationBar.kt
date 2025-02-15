package com.app.motus_finance.presentation.view.Navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus_finance.R
import com.app.motus_finance.data.models.NavigationBarBottom
import com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.MainScreen.MainScreen
import com.app.motus_finance.presentation.view.ui.theme.MainColor
import com.app.motus_finance.presentation.view.ui.theme.secondaryColor
import com.app.motus_finance.presentation.viewmodel.MarketViewModel
import com.app.motus_finance.presentation.viewmodel.PaymentsViewModel

@Composable
fun NavigationBarComposable(marketViewModel: MarketViewModel, paymentsViewModel: PaymentsViewModel, navController: NavController){
    val itemsNavigationBottom = listOf(
        NavigationBarBottom(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationBarBottom(
            title = "Graphics",
            selectedIcon = ImageVector.vectorResource(R.drawable.baseline_auto_graph_24),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_auto_graph_24)
        ),
        NavigationBarBottom(
            title = "Classifications",
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List
        ),
        NavigationBarBottom(
            title = "Config",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )
    Scaffold(bottomBar = {
        Box{
            NavigationBar(
                containerColor = MainColor
            ){
                itemsNavigationBottom.forEachIndexed{ index, item ->
                    NavigationBarItem(
                        selected = marketViewModel.selectedTab.value == index,
                        onClick = {
                            marketViewModel.selectedTab.value = index
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = Color.White,
                            indicatorColor = Color.Transparent,
                            selectedIconColor = secondaryColor
                        ),
                        icon = {
                            Icon(
                                imageVector = if(index == marketViewModel.selectedTab.value){
                                    item.selectedIcon
                                }else{
                                    item.unselectedIcon
                                },
                                contentDescription = item.title
                            )
                        })
                }
            }
            FloatingActionButton(
                onClick = {
                   navController.navigate("marketOrShopping")
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
                    .offset(y = (-70).dp),
                shape = RoundedCornerShape(20.dp),
                containerColor = MainColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }

    }
        ,content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (marketViewModel.selectedTab.value) {
                    0 -> MainScreen(marketViewModel, paymentsViewModel, navController)
                }
            }
        })
}
