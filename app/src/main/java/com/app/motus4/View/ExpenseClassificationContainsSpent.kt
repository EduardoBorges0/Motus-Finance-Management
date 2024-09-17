@file:Suppress("DEPRECATION")

package com.app.motus2.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.simplemoney.Models.Room.Bank
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.motus4.R
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.simplemoney8.View.NavBottom.toColor
import com.app.simplemoney8.customFontFamily
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExpenseClassificationBank(viewModel: BankViewModel,
                              expenseViewModel: ExpenseViewModel,
                              classification: String?,
                              navController: NavController) {
    val classifications = listOf(
        stringResource(id = R.string.transporte),
        stringResource(id = R.string.esporte),
        stringResource(id = R.string.lazer),
        stringResource(id = R.string.servicos),
        stringResource(id = R.string.contas),
        stringResource(id = R.string.reserva_de_emergencia),
        stringResource(id = R.string.mercado),
        stringResource(id = R.string.saude),
        stringResource(id = R.string.investimento),
        stringResource(id = R.string.animal_de_estimacao),
        stringResource(id = R.string.viagens),
        stringResource(id = R.string.educacao),
        stringResource(id = R.string.outros)
    )
    val banks by viewModel.getAllBanks().observeAsState(emptyList())

    val classificationTotals = remember { mutableStateMapOf<String, Double>() }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        classifications.forEach { classification ->
            expenseViewModel.getTotalByClassification(classification) { total ->
                classificationTotals[classification] = total ?: 0.0
            }
        }
    }

    val filteredBanks = banks.filter { bank ->
        val expensesClassification by expenseViewModel.getExpenseForClassification(bank.id, classification).observeAsState(emptyList())
        expensesClassification.isNotEmpty()
    }

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
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = DarkBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "return")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 90.dp)
                        .weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        items(filteredBanks, key = { it.id }) { bank ->
                            BankItemNavBottom(
                                bank = bank,
                                viewModel = viewModel,
                                refreshing = refreshing,
                                onRefreshComplete = { refreshing = false },
                                classification = classification,
                                expenseViewModel = expenseViewModel
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun BankItemNavBottom(
    bank: Bank,
    viewModel: BankViewModel,
    expenseViewModel: ExpenseViewModel,
    refreshing: Boolean,
    classification: String?,
    onRefreshComplete: () -> Unit
) {
    val expensesClassification by expenseViewModel.getExpenseForClassification(bank.id, classification).observeAsState(emptyList())
    var lang by remember { mutableStateOf("pt") }

    val totalByClassificationAndBankId = remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(Unit) {
        val expensesClassificationByBankId = expenseViewModel.getTotalByClassificationAndBankId(bank.id, classification.toString())
        totalByClassificationAndBankId.value = expensesClassificationByBankId
        lang = viewModel.updateLanguage() ?: "pt"

    }
    val locale = when (lang) {
        "pt" -> Locale("pt", "BR")
        "en" -> Locale("en", "US")
        "es" -> Locale("es", "ES")
        else -> Locale.getDefault()
    }
    val currencyFormat = NumberFormat.getCurrencyInstance(locale)

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            onRefreshComplete()
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
                model = bank.img,
                contentDescription = "bankImage",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(70.dp)
            )
            Text(
                text = "${bank.creditOrDebit}",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontFamily = customFontFamily

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
        Text(
            text = totalByClassificationAndBankId.value?.let {
                currencyFormat.format(it)
            } ?: "N/A",
            color = Color.White,
            modifier = Modifier
                .padding(top = 25.dp)
                .padding(horizontal = 10.dp),
            fontFamily = customFontFamily
        )

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
            expensesClassification.forEach { expense ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(bank.colorSpentsOrReceived.toColor())
                        .height(55.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${expense.description}",
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        fontFamily = customFontFamily
                    )
                    Text(
                        text = if (expense.spentOrReceived == "Spent") {
                            "- ${currencyFormat.format(expense.value)}"
                        } else {
                            currencyFormat.format(expense.value)
                        },
                        color = if (expense.spentOrReceived == "Spent") Color.Red else Color.Green,
                        modifier = Modifier.padding(end = 10.dp),
                        fontFamily = customFontFamily
                    )
                }
            }
        }
    }
}
