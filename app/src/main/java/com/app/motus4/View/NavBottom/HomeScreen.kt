@file:Suppress("DEPRECATION")

package com.app.simplemoney8.View.NavBottom

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.simplemoney.Models.Room.Bank
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.simplemoney.ui.theme.DarkBlueDivisor
import com.app.simplemoney.ui.theme.GoldYellow
import com.app.motus4.R
import com.app.motus2.View.NavBottom.ProfileScreenContent
import com.app.motus4.Models.Room.DataClass.ModelPayment
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.motus4.ViewModels.PaymentViewModel.PaymentViewModel
import com.app.simplemoney.ui.theme.negative
import com.app.simplemoney6.Models.Room.DataClass.Expense
import com.app.simplemoney8.customFontFamily
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

@Composable
fun HomeScreenComposable(viewModel: BankViewModel, navHostController: NavHostController, expenseViewModel: ExpenseViewModel, paymentViewModel: PaymentViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("", "", "", "")

    // Crie os ícones usando ImageVector e Painter
    val homeIcon = Icons.Outlined.Home
    val stackedIcon = painterResource(id = R.drawable.stacked)
    val listIcon = Icons.Outlined.List
    val settingsIcon = Icons.Outlined.Settings

    Scaffold(
        bottomBar = {
            Column {
                // Linha acima do NavigationBar
                Divider(
                    color = DarkBlueDivisor, // Cor da linha
                    thickness = 1.dp, // Espessura da linha
                    modifier = Modifier.fillMaxWidth() // Preencher toda a largura
                )

                Box {
                    NavigationBar(
                        containerColor = DarkBlue,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        tabs.forEachIndexed { index, title ->
                            if (index == 2) {
                                Spacer(modifier = Modifier.weight(1f, fill = true))
                            }
                            NavigationBarItem(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                icon = {
                                    when (index) {
                                        0 -> Icon(homeIcon, contentDescription = title)
                                        1 -> Icon(painter = stackedIcon, contentDescription = title)
                                        2 -> Icon(listIcon, contentDescription = title)
                                        3 -> Icon(settingsIcon, contentDescription = title)
                                    }
                                },
                                label = { Text(title) },
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    selectedIconColor = GoldYellow,
                                    selectedTextColor = GoldYellow,
                                    indicatorColor = DarkBlue
                                )
                            )
                        }
                    }
                    FloatingActionButton(
                        onClick = {
                            navHostController.navigate("creditOrDebit")
                        },
                        contentColor = Color.White,
                        containerColor = DarkBlue,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp)
                            .offset(y = (-30).dp)
                            .border(
                                width = 1.dp,
                                color = DarkBlueDivisor,
                                shape = RoundedCornerShape(14.dp)
                            )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    0 -> HomeScreenContent(viewModel, navHostController, expenseViewModel, paymentViewModel)
                    1 -> StatisticScreenContent(expenseViewModel, viewModel)
                    2 -> ProfileScreenContent(viewModel, navHostController, expenseViewModel)
                    3 -> SettingsScreenContent(navController = navHostController)
                }
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    viewModel: BankViewModel,
    navHostController: NavHostController,
    expenseViewModel: ExpenseViewModel,
    paymentViewModel: PaymentViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    val banks by viewModel.getAllBanks().observeAsState(emptyList())
    var refreshing by remember { mutableStateOf(false) }


    // Obtenha o LiveData usando observeAsState
    val payment by paymentViewModel.livedata.observeAsState()

    var lang by remember { mutableStateOf("pt") }

    LaunchedEffect(Unit) {
        // Update the language setting
        paymentViewModel.getPayment()
        lang = viewModel.updateLanguage() ?: "pt"
    }

    val locale = when (lang) {
        "pt" -> Locale("pt", "BR")
        "en" -> Locale("en", "US")
        "es" -> Locale("es", "ES")
        else -> Locale.getDefault()
    }

    val currencyFormat = NumberFormat.getCurrencyInstance(locale)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (payment != null) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .border(1.dp, DarkBlue, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        payment.let {
                            Log.d("ISSO AI", "${it?.payment}")
                            Text(
                                text = currencyFormat.format(it?.payment).toString(),
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(end = 40.dp) // Add space for the icon
                            )                        }
                        // Close button "X"
                        IconButton(
                            onClick = {
                                showDialog = true
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp) // Set icon size
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Close Icon",
                                tint = Color.Black
                            )
                        }
                    }
                } else {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .border(1.dp, DarkBlue, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            navHostController.navigate("payment")
                        }
                    ) {
                        Text("Selecione aqui seu salário!!")
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        items(banks, key = { it.id }) { bank ->
                            BankItem(
                                bank = bank,
                                viewModel = viewModel,
                                refreshing = refreshing,
                                onRefreshComplete = { refreshing = false },
                                navHostController = navHostController,
                                expenseViewModel = expenseViewModel,
                                paymentViewModel = paymentViewModel
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }

            // Dialog for deleting payment
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = stringResource(id = R.string.deletar_banco)) },
                    text = { Text(text = stringResource(id = R.string.voce_ira_deletar_banco)) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            CoroutineScope(Dispatchers.IO).launch {
                                paymentViewModel.deletePayment(payment!!.id)
                                withContext(Dispatchers.Main) {
                                    paymentViewModel.getPayment() // Chama novamente para atualizar o LiveData
                                }
                            }
                        }) {
                            Text(text = "OK", color = Color.Black)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                        }) {
                            Text(text = "Cancelar", color = Color.Black)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun BankItem(
    bank: Bank,
    viewModel: BankViewModel,
    expenseViewModel: ExpenseViewModel,
    refreshing: Boolean,
    navHostController: NavHostController,
    paymentViewModel: PaymentViewModel,
    onRefreshComplete: () -> Unit
) {
    var lang by remember { mutableStateOf("pt") }

    LaunchedEffect(Unit) {
        lang = viewModel.updateLanguage() ?: "pt"
    }
        val locale = when (lang) {
            "pt" -> Locale("pt", "BR")
            "en" -> Locale("en", "US")
            "es" -> Locale("es", "ES")
            else -> Locale.getDefault()
        }
        val currencyFormat = NumberFormat.getCurrencyInstance(locale)
        var expenseToDelete by remember { mutableStateOf<Expense?>(null) }
        val expenses by expenseViewModel.getExpensesForBank(bank.id).observeAsState(emptyList())
        var showDialog by remember { mutableStateOf(false) }
        var showDialogBank by remember { mutableStateOf(false) }
    expenses.forEach { expense ->
        expenseViewModel.deleteExpenseByBankId(
            expense.bankId!!, expense.type.toString(),
            bank.date.toString(),
            expense.spentOrReceived
        )
    }


        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
                onRefreshComplete()
                expenses.forEach { expense ->
                    expenseViewModel.deleteExpenseByBankId(
                        expense.bankId!!, expense.type.toString(),
                        bank.date.toString(),
                        expense.spentOrReceived
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bank.color.toColor())
                .padding(18.dp)
                .fillMaxSize()
                .heightIn(min = 260.dp)
        ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 10.dp)
                ) {
                    AsyncImage(
                        model = bank.img.toInt(),
                        contentDescription = "bankImage",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(70.dp)
                    )
                    Text(
                        text = "${bank.nameOfExpenses}",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 5.dp),
                        fontFamily = customFontFamily
                    )
                }

            if (bank.balance!! < 0) {
                if(bank.name == "Bradesco"){
                    Text(
                        text = currencyFormat.format(bank.balance).toString(),
                        color = negative,
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .padding(horizontal = 10.dp),
                        fontFamily = customFontFamily
                    )
                }
            } else {
                Text(
                    text = currencyFormat.format(bank.balance).toString(),
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .padding(horizontal = 10.dp),
                    fontFamily = customFontFamily
                )
            }

            Text(
                text = "${bank.date}",
                color = Color.White,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .padding(horizontal = 10.dp),
                fontFamily = customFontFamily
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 120.dp)

            ) {
                expenses.forEach { expense ->
                    val isVisible = !expense.readyForDeletion

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (expense.readyForDeletion) Color.Red else bank.colorSpentsOrReceived.toColor())
                            .height(if (isVisible) 65.dp else 0.dp) // Controla a altura para ocultar o item
                            .fillMaxWidth()
                            .alpha(if (isVisible) 1f else 0f) // Controla a visibilidade com alpha
                            .clickable {
                                if (isVisible) {
                                    showDialog = true
                                    expenseToDelete = expense
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (isVisible) {
                            Text(
                                text = "${expense.description}",
                                    color = if(bank.name == "Santander") Color.Black else Color.White ,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp),
                                fontFamily = customFontFamily
                            )
                            Column {
                                Text(
                                    text = expense.date.toString(),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.End).padding(end = 10.dp),
                                    fontSize = 15.sp,
                                    fontFamily = customFontFamily
                                )
                                Spacer(modifier = Modifier.height(5.dp))

                                if(bank.name == "Bradesco"){
                                    Text(
                                        text = if (expense.spentOrReceived == "Spent") {
                                            "- ${currencyFormat.format(expense.value)}"
                                        } else {
                                            currencyFormat.format(expense.value).toString()
                                        },
                                        color = if (expense.spentOrReceived == "Spent") negative else Color.Green,
                                        modifier = Modifier.padding(end = 10.dp),
                                        fontFamily = customFontFamily
                                    )
                                }else{
                                    Text(
                                        text = if (expense.spentOrReceived == "Spent") {
                                            "- ${currencyFormat.format(expense.value)}"
                                        } else {
                                            currencyFormat.format(expense.value).toString()
                                        },
                                        color = if (expense.spentOrReceived == "Spent") Color.Red else Color.Green,
                                        modifier = Modifier.padding(end = 10.dp),
                                        fontFamily = customFontFamily
                                    )
                                }

                            }


                        }
                    }

                    if (showDialog && expenseToDelete != null) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = {
                                Text(
                                    text = stringResource(id = R.string.deletar_gasto),
                                    fontFamily = customFontFamily
                                )
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.voce_ira_deletar_gastos),
                                    fontFamily = customFontFamily
                                )
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                        expenseToDelete?.let { expense ->
                                            val value = expense.value ?: 0.0
                                            expenseViewModel.deleteExpenseById(
                                                bank,
                                                expense.id,
                                                value,
                                                expense.spentOrReceived.toString()
                                            )
                                            CoroutineScope(Dispatchers.IO).launch {
                                                paymentViewModel.updatePaymentForDeleteExpense(value = value, expense.spentOrReceived.toString())

                                            }
                                        }

                                    }
                                ) {
                                    Text(
                                        text = "OK",
                                        color = Color.Black,
                                        fontFamily = customFontFamily
                                    )
                                }
                            }
                        )
                    }
                }



                if (showDialog && expenseToDelete != null) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = {
                            Text(
                                text = stringResource(id = R.string.deletar_gasto),
                                fontFamily = customFontFamily
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(id = R.string.voce_ira_deletar_gastos),
                                fontFamily = customFontFamily
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDialog = false
                                    expenseToDelete?.let { expense ->
                                        val value = expense.value ?: 0.0
                                        expenseViewModel.deleteExpenseById(
                                            bank,
                                            expense.id,
                                            value,
                                            expense.spentOrReceived.toString()
                                        )
                                        CoroutineScope(Dispatchers.IO).launch {
                                            paymentViewModel.updatePaymentForDeleteExpense(value = value, expense.spentOrReceived.toString())
                                        }
                                    }

                                }

                            ) {
                                Text(
                                    text = "OK",
                                    color = Color.Black,
                                    fontFamily = customFontFamily
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }) {
                                Text(text = "Cancelar", color = Color.Black)
                            }
                        }
                    )
                }


                if (showDialogBank) {
                    AlertDialog(
                        onDismissRequest = { showDialogBank = false },
                        title = { Text(text = stringResource(id = R.string.deletar_banco)) },
                        text = { Text(text = stringResource(id = R.string.voce_ira_deletar_banco)) },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialogBank = false
                                paymentViewModel.updatePaymentByBank(bank.id)
                                viewModel.deleteBank(bank)
                            }) {
                                Text(text = "OK", color = Color.Black)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialogBank = false
                            }) {
                                Text(text = "Cancelar", color = Color.Black)
                            }
                        }
                    )
                }
                Button(
                    onClick = {
                        navHostController.navigate("expense?bankId=${bank.id}")
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 40.dp)
                        .padding(top = 40.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bank.colorSpentsOrReceived.toColor()
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.adicione_seus_gastos),
                        color = if (bank.name == "Santander") Color.Black else Color.White,
                        fontFamily = customFontFamily
                    )
                }
            }

            Button(
                onClick = {
                    showDialogBank = true
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "IconBank",
                    tint = Color.White
                )
            }
        }

}



