package com.app.motus2.View.NavBottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.motus4.R
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.simplemoney8.TranslatedExpenseName
import com.app.simplemoney8.customFontFamily
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProfileScreenContent(viewModel: BankViewModel, navController: NavHostController, expenseViewModel: ExpenseViewModel) {
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


    val classificationTotals = remember { mutableStateMapOf<String, Double>() }

    LaunchedEffect(Unit) {
        classifications.forEach { classification ->
            expenseViewModel.getTotalByClassification(TranslatedExpenseName(classification)) { total ->
                classificationTotals[classification] = total ?: 0.0
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        classifications.forEach { classification ->
            ExpenseClassificationBox(
                icon = null,
                expenseClassification = classification,
                totalValue = classificationTotals[classification],
                navController = navController,
                viewModel = viewModel
            )
        }
    }

}


@Composable
fun ExpenseClassificationBox(
    icon: String?,
    expenseClassification: String?,
    totalValue: Double?,
    viewModel: BankViewModel,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
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
    Box(
        modifier = Modifier
            .padding(bottom = 1.dp)
            .background(DarkBlue)
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                if (totalValue == 0.0) {
                    showDialog = true
                } else {
                    navController.navigate("expenseClassificationNavBottom?classification=$expenseClassification")

                }
            }
    ) {
        Text(
            text = icon.orEmpty(),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = customFontFamily
        )
        Text(
            text = expenseClassification.orEmpty(),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 70.dp),
            fontFamily = customFontFamily
        )
        Text(
            text = totalValue?.let { currencyFormat.format(it) }.orEmpty().toString(),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 65.dp),
            fontFamily = customFontFamily
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 15.dp),
            tint = Color.White
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.sem_gastos), fontFamily = customFontFamily) },
            text = { Text(text = stringResource(id = R.string.voce_nao_gastou_nada_nessa_categoria), fontFamily = customFontFamily) },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "OK", color = Color.Black, fontFamily = customFontFamily)
                }
            }
        )
    }
}
