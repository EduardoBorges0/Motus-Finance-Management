package com.app.motus4.View

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.motus4.Models.RepositoryPayment
import com.app.motus4.Models.Room.DataClass.ModelPayment
import com.app.motus4.ViewModels.PaymentViewModel.PaymentViewModel
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.simplemoney8.customFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SelectPayment(navController: NavController, viewModel: PaymentViewModel) {
    var payment by remember { mutableStateOf("0.00") }
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
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))  // Ocupa espaço acima do campo de texto
        TextField(
            value = payment,
            onValueChange = {
                // Permite apenas dígitos, ponto e vírgula
                if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) {
                    val cleanedInput = it.replace(",", "").replace(".", "")

                    // Garante no mínimo 2 casas decimais
                    val parsedValue = cleanedInput.toLongOrNull() ?: 0L

                    // Formata como valor monetário (centavos)
                    payment = if (parsedValue == 0L) {
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
        Text("Seu salário")

        Spacer(modifier = Modifier.weight(1f))  // Ocupa espaço abaixo do campo de texto

        Button(
            onClick = {
                val cleanedBalance = payment.replace(",", ".")
                if(cleanedBalance.count{ it == '.' } == 2){
                    val removed = cleanedBalance.replaceFirst(".", "")
                    Log.d("REMOVEU", "VAI VAI ${removed}")
                    CoroutineScope(Dispatchers.IO).launch {
                        val model = ModelPayment(
                            payment = removed.toDouble()
                        )
                        viewModel.insertPayment(model)
                        viewModel.updatePayment()
                    }
                    navController.navigate("home")
                }
                else if ( cleanedBalance.count{ it == '.' } == 3 ){
                    val removed = cleanedBalance.replaceFirst(".", "")
                    val removedSecond = removed.replaceFirst(".", "")
                    Log.d("REMOVEU 3", "VAI VAI ${removedSecond}")
                    CoroutineScope(Dispatchers.IO).launch {
                        val model = ModelPayment(
                            payment = removed.toDouble()
                        )
                        viewModel.updatePayment()

                        viewModel.insertPayment(model)
                    }
                    navController.navigate("home")
                }
                else{
                    CoroutineScope(Dispatchers.IO).launch {
                        val model = ModelPayment(
                            payment = payment.toDouble()
                        )
                        viewModel.updatePayment()

                        viewModel.insertPayment(model)
                    }
                    navController.navigate("home")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Adicionar", fontFamily = customFontFamily)
        }
    }
}
