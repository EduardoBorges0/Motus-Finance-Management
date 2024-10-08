package com.app.simplemoney8.View.Expenses

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.simplemoney.Models.Room.Bank
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.R
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.simplemoney8.customFontFamily


@Composable
fun ExpensesComposable(
    bankId: Int,
    viewModel: BankViewModel,
    expenseViewModel: ExpenseViewModel,
    bank: Bank?,
    navController: NavController,
) {
    var expense by remember { mutableStateOf("") }
    var expenseValue by remember { mutableStateOf("0.00") }
    var selectedOptionSpentOrReceived by remember { mutableStateOf<String?>(null) }
    var selectedExpenseType by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F1F5))
            .clip(RoundedCornerShape(24.dp))
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
                contentDescription = "return"
            )
        }
        Column(modifier = Modifier.align(Alignment.Center).padding(top = 30.dp)) {
            Image(painter = painterResource(bank?.img!!.toInt()),
                contentDescription = "IMG",
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .align(Alignment.CenterHorizontally))
            TextField(
                value = expense,
                onValueChange = { expense = it },
                label = { Text(text = stringResource(id = R.string.gasto_recebido)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(top = 40.dp),
                textStyle = TextStyle(color = Color.Black),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OptionReceivedOrSpent(
                    text = stringResource(id = R.string.gasto),
                    isSelected = selectedOptionSpentOrReceived == "Spent",
                    onClick = { selectedOptionSpentOrReceived = "Spent" }
                )
                OptionReceivedOrSpent(
                    text = stringResource(id = R.string.recebido),
                    isSelected = selectedOptionSpentOrReceived == "Received",
                    onClick = { selectedOptionSpentOrReceived = "Received" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OptionReceivedOrSpent(
                    text = stringResource(id = R.string.fixo),
                    isSelected = selectedExpenseType == "Fixed",
                    onClick = { selectedExpenseType = "Fixed" }
                )
                OptionReceivedOrSpent(
                    text = stringResource(id = R.string.variavel),
                    isSelected = selectedExpenseType == "Variable",
                    onClick = { selectedExpenseType = "Variable" }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = expenseValue,
                onValueChange = {
                    // Permite apenas dígitos, ponto e vírgula
                    if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) {
                        val cleanedInput = it.replace(",", "").replace(".", "")

                        // Garante no mínimo 2 casas decimais
                        val parsedValue = cleanedInput.toLongOrNull() ?: 0L

                        // Formata como valor monetário (centavos)
                        expenseValue = if (parsedValue == 0L) {
                            "0,00"
                        } else {
                            val formattedValue = parsedValue.toString().padStart(3, '0')
                            val integerPart = formattedValue.dropLast(2)
                            val decimalPart = formattedValue.takeLast(2)

                            // Adiciona pontos como separador de milhar
                            val integerWithThousandsSeparator =
                                integerPart.reversed().chunked(3).joinToString(".").reversed()

                            "$integerWithThousandsSeparator.$decimalPart"
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                textStyle = TextStyle(color = Color.Black, textAlign = TextAlign.Center, fontSize = 50.sp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            // Botão de confirmação
            Button(
                onClick = {
                    if (expense.isEmpty() || expenseValue.isEmpty() || selectedOptionSpentOrReceived == null || selectedExpenseType == null) {
                        showDialog = true
                    } else {
                        val cleanedBalance = expenseValue.replace(",", ".")
                        if(cleanedBalance.count{ it == '.' } == 2){
                            val removed = cleanedBalance.replaceFirst(".", "")
                            if (selectedOptionSpentOrReceived == "Spent") {
                                navController.navigate("expenseClassification?bankId=$bankId&expense=$expense&expenseValue=$removed&selectedOptionSpentOrReceived=$selectedOptionSpentOrReceived&selectedExpenseType=$selectedExpenseType&date=${bank?.date}")
                            } else {
                                expenseViewModel.insertExpense(
                                    bankId,
                                    expense,
                                    removed.toDouble(),
                                    selectedOptionSpentOrReceived.toString(),
                                    selectedExpenseType,
                                    bank?.date.toString(),
                                    ""
                                )
                                viewModel.updateBalanceForExpense(bankId, cleanedBalance.toDouble(), selectedOptionSpentOrReceived.toString())
                                navController.navigate("home")
                            }
                        }
                        else if ( cleanedBalance.count{ it == '.' } == 3 ){
                            val removed = cleanedBalance.replaceFirst(".", "")
                            val removedSecond = removed.replaceFirst(".", "")
                            if (selectedOptionSpentOrReceived == "Spent") {
                                navController.navigate("expenseClassification?bankId=$bankId&expense=$expense&expenseValue=$removedSecond&selectedOptionSpentOrReceived=$selectedOptionSpentOrReceived&selectedExpenseType=$selectedExpenseType&date=${bank?.date}")
                            } else {
                                expenseViewModel.insertExpense(
                                    bankId,
                                    expense,
                                    removedSecond.toDouble(),
                                    selectedOptionSpentOrReceived.toString(),
                                    selectedExpenseType,
                                    bank?.date.toString(),
                                    ""
                                )
                                viewModel.updateBalanceForExpense(bankId, removedSecond.toDouble(), selectedOptionSpentOrReceived.toString())
                                navController.navigate("home")
                            }
                        }
                        else{
                            if (selectedOptionSpentOrReceived == "Spent") {
                                navController.navigate("expenseClassification?bankId=$bankId&expense=$expense&expenseValue=$cleanedBalance&selectedOptionSpentOrReceived=$selectedOptionSpentOrReceived&selectedExpenseType=$selectedExpenseType&date=${bank?.date}")
                            } else {
                                expenseViewModel.insertExpense(
                                    bankId,
                                    expense,
                                    cleanedBalance.toDouble(),
                                    selectedOptionSpentOrReceived.toString(),
                                    selectedExpenseType,
                                    bank?.date.toString(),
                                    ""
                                )
                                viewModel.updateBalanceForExpense(bankId, cleanedBalance.toDouble(), selectedOptionSpentOrReceived.toString())
                                navController.navigate("home")
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 190.dp)
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .height(56.dp)
                    .padding(horizontal = 29.dp),
                colors = ButtonDefaults.buttonColors(DarkBlue)
            ) {
                Text(text = stringResource(id = R.string.confirmar), style = TextStyle(color = Color.White), fontFamily = customFontFamily)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Validation Error") },
            text = { Text(text = "All fields are required.") },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "OK", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun OptionReceivedOrSpent(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 18.sp, color = if (isSelected) DarkBlue else Color.Black),
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = customFontFamily
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = DarkBlue,
                unselectedColor = Color.Gray
            )
        )
    }
}
