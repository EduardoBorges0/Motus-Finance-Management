package com.app.simplemoney8.View.Expenses

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.app.simplemoney.Models.Room.Bank
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.motus4.R
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.motus4.ViewModels.PaymentViewModel.PaymentViewModel
import com.app.simplemoney8.TranslatedExpenseName
import com.app.simplemoney8.customFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun ExpenseClassificationComposable(navController: NavController,
                                    viewModel: BankViewModel,
                                    expenseViewModel: ExpenseViewModel,
                                    bankId: Int,
                                    expense: String,
                                    expenseValue: Double,
                                    date: String?,
                                    selectedOptionSpentOrReceived: String,
                                    selectedExpenseType : String?,
                                    paymentViewModel: PaymentViewModel){
  val list = listOf(
      ExpenseClassificationBank("", stringResource(id = R.string.transporte), null) ,
      ExpenseClassificationBank("", stringResource(id = R.string.esporte), null),
      ExpenseClassificationBank("", stringResource(id = R.string.lazer), null),
      ExpenseClassificationBank("", stringResource(id = R.string.servicos), null),
      ExpenseClassificationBank("", stringResource(id = R.string.contas), null),
      ExpenseClassificationBank("", stringResource(id = R.string.reserva_de_emergencia), null),
      ExpenseClassificationBank("", stringResource(id = R.string.mercado), null),
      ExpenseClassificationBank("", stringResource(id = R.string.saude), null),
      ExpenseClassificationBank("", stringResource(id = R.string.investimento), null),
      ExpenseClassificationBank("", stringResource(id = R.string.animal_de_estimacao), null),
      ExpenseClassificationBank("", stringResource(id = R.string.viagens), null),
      ExpenseClassificationBank("", stringResource(id = R.string.educacao), null),
      ExpenseClassificationBank("", stringResource(id = R.string.outros), null)

  )
    Column (
     modifier = Modifier
         .fillMaxSize()
         .background(Color.White)
         .verticalScroll(rememberScrollState())
    ){
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(bottom = 1.dp)
                .fillMaxWidth()
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "return"
                )
            }
        }

        list.forEach{
            ExpenseClassificationBox(icon = it.icon,
                ExpenseClassification = it.ExpenseClassification,
                viewModel = viewModel,
                bankId = bankId,
                expense = expense,
                expenseValue = expenseValue,
                selectedOptionSpentOrReceived = selectedOptionSpentOrReceived,
            selectedExpenseType = selectedExpenseType,
                date = date,
                navController = navController,
                expenseViewModel = expenseViewModel,
                paymentViewModel = paymentViewModel
            )
        }
    }

}

@Composable
fun ExpenseClassificationBox(navController: NavController,
                             icon : String?,
                             ExpenseClassification: String?,
                             viewModel: BankViewModel,
                             expenseViewModel: ExpenseViewModel,
                             bankId: Int,
                             expense: String,
                             expenseValue: Double,
                             selectedOptionSpentOrReceived: String,
                             selectedExpenseType : String?,
                             date: String?,
                             paymentViewModel: PaymentViewModel){
    var showDialog by remember { mutableStateOf(false) }
    Box (
        modifier = Modifier
            .padding(bottom = 1.dp)
            .background(DarkBlue)
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                navController.navigate("home")
                if (expense.isEmpty() || expenseValue
                        .toString()
                        .isEmpty() || selectedExpenseType == null
                ) {
                    showDialog = true
                } else {
                    val translatedExpenseClassification = TranslatedExpenseName(
                        ExpenseClassification.toString()
                    )

                    expenseViewModel.insertExpense(
                        bankId,
                        expense,
                        expenseValue,
                        selectedOptionSpentOrReceived,
                        selectedExpenseType,
                        date = date,
                        expenseClassification = translatedExpenseClassification
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        paymentViewModel.updatePaymentIsNotExist(expenseValue.toDouble(), selectedOptionSpentOrReceived.toString())
                    }
                    viewModel.updateBalanceForExpense(
                        bankId,
                        expenseValue,
                        selectedOptionSpentOrReceived
                    )

                }
            },
    ){
        Text(
            text = icon.toString(),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = customFontFamily

        )
        Text(
            text = ExpenseClassification.toString(),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 50.dp),
            fontFamily = customFontFamily
        )
        Icon(imageVector = Icons.Filled.KeyboardArrowRight
            ,contentDescription = ""
            ,modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 15.dp)
            ,tint = Color.White
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Validation Error", fontFamily = customFontFamily) },
            text = { Text(text = "All fields are required.", fontFamily = customFontFamily) },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "OK", color = Color.White, fontFamily = customFontFamily)
                }
            }
        )
    }

}

data class ExpenseClassificationBank(
    val icon : String?,
    val ExpenseClassification : String?,
    val totalValue : Double?
)