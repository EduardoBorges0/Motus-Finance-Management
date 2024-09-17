package com.app.motus2.View

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.R
import com.app.simplemoney8.customFontFamily
import java.util.Calendar

@Composable
fun CreditOrDebitComposable(navHostController: NavHostController) {
    var balance by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var nameOfBank by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier

            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .clip(RoundedCornerShape(24.dp))
    ) {
        Button(
            onClick = { navHostController.popBackStack() },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = nameOfBank,
                onValueChange = {
                    nameOfBank = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 30.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                textStyle = TextStyle(color = Color.Black, textAlign = TextAlign.Center),
                label = {
                    Text(text = stringResource(id = R.string.nome_do_que_sao_esses_gastos), color = Color.Gray)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            TextField(
                value = balance,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) {
                        balance = it
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                textStyle = TextStyle(color = Color.Black, textAlign = TextAlign.Center),
                label = {
                    Text(text = stringResource(id = R.string.seu_saldo), color = Color.Gray)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            DateTextField(
                label = stringResource(id = R.string.selecione_a_data_de_vencimento),
                initialDate = date
            ) { selectedDate ->
                date = selectedDate
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OptionRow(
                    text = stringResource(id = R.string.credito),
                    isSelected = selectedOption == "Credit",
                    onClick = { selectedOption = "Credit" }
                )
                OptionRow(
                    text = stringResource(id = R.string.debito),
                    isSelected = selectedOption == "Debit",
                    onClick = { selectedOption = "Debit" }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (balance.isEmpty() || selectedOption == null || date.isEmpty() || nameOfBank.isEmpty()) {
                        showDialog = true
                    } else {
                        navHostController.navigate("addImage?balance=$balance&creditOrDebit=$selectedOption&date=$date&nameOfBank=$nameOfBank")

                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(DarkBlue)
            ) {
                Text(text = stringResource(id = R.string.confirmar), style = TextStyle(color = Color.White), fontFamily = customFontFamily
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.erro_de_validacao)) },
            text = { Text(text = stringResource(id = R.string.por_favor_preencha_todos_os_campos)) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "OK", color = Color.Black)
                }
            }
        )
    }
}

@Composable
fun DateTextField(
    label: String,
    initialDate: String,
    onDateSelected: (String) -> Unit
) {
    var date by remember { mutableStateOf(initialDate) }
    val context = LocalContext.current

    TextField(
        value = date,
        onValueChange = { },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        textStyle = TextStyle(color = Color.Black),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            cursorColor = DarkBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.clickable {
                    showDatePicker(context) { selectedDate ->
                        date = selectedDate
                        onDateSelected(selectedDate)
                    }
                }
            )
        }
    )
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "${selectedDay.toString().padStart(2, '0')}/" +
                    "${(selectedMonth + 1).toString().padStart(2, '0')}/$selectedYear"
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

@Composable
fun OptionRow(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
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
