package com.app.simplemoney8.View.NavBottom

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.app.motus4.R
import com.app.motus4.View.Chart
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.simplemoney8.TranslatedExpenseName
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.exp

@Composable
fun StatisticScreenContent(viewModel: ExpenseViewModel, bankViewModel: BankViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
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
    val totalSpent = remember { mutableStateOf(0.0) }
    val classificationPercentages = remember { mutableStateMapOf<String, Float>() }
    val currentChartIndex = remember { mutableStateOf(0) }
    val monthAll by viewModel.getAllMonthlyExpense().observeAsState(emptyList())
    val expenseMonthPairs = monthAll.mapNotNull { expense ->
        val expenseFloat = expense?.monthlyExpense?.toFloat()
        val monthString = expense?.monthly ?: ""
        expenseFloat?.let { expenseFloat to monthString }
    }

    // Converte a lista de pares para um Map
    val expenseMonthMap: Map<Float, String> = expenseMonthPairs.toMap()


    Log.d("PARES", "OS PARES SAO ESSES $expenseMonthPairs")
    LaunchedEffect(Unit) {
        val totalSpentValue = viewModel.getTotalSpent() ?: 0.0
        totalSpent.value = totalSpentValue

        classifications.forEach { classification ->
            viewModel.getTotalByClassification(TranslatedExpenseName(classification)) { total ->
                val totalAmount = total ?: 0.0
                classificationTotals[classification] = totalAmount
                val percentage = if (totalSpentValue > 0) {
                    (totalAmount / totalSpentValue * 100).toFloat()
                } else {
                    0f
                }
                classificationPercentages[classification] = percentage
            }
        }
    }
    val maxValue = monthAll
        .mapNotNull { it?.monthlyExpense?.toDouble() } // Filtra valores nulos e converte para Double
        .maxOfOrNull { it } // Encontra o maior valor ou null se a lista estiver vazia

    val maxValueZero = maxValue ?: 0.0


    val pieChartData = PieChartData(
        slices = classifications.map { classification ->
            PieChartData.Slice(
                label = classification,
                value = classificationPercentages[classification] ?: 0f,
                color = getColorForClassification(classification)
            )
        },
        plotType = PlotType.Pie
    )

    val donutChartData = PieChartData(
        slices = classifications.map {
            PieChartData.Slice(
                label = it,
                value = classificationPercentages[it] ?: 0f,
                color = getColorForClassification(classification = it)
            )
        },
        plotType = PlotType.Donut
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = false,
        animationDuration = 1500
    )
    if (screenWidth < 400) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            when (currentChartIndex.value) {
                0 -> PieChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 20.dp)
                        .offset(y = -50.dp)
                        .align(Alignment.Center),
                    pieChartData = pieChartData,
                    pieChartConfig = pieChartConfig
                )

                1 -> PieChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 20.dp)
                        .offset(y = -50.dp)
                        .align(Alignment.Center),
                    pieChartData = donutChartData,
                    pieChartConfig = pieChartConfig
                )

                2 -> Chart(data = expenseMonthMap, maxValue = maxValueZero.toInt(), viewModel)
            }

            Button(
                onClick = {
                    currentChartIndex.value = (currentChartIndex.value + 1) % 3
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = -240.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Next Chart",
                    tint = DarkBlue,
                    modifier = Modifier.size(50.dp)
                )
            }

            Button(
                onClick = {
                    currentChartIndex.value = if (currentChartIndex.value > 0) {
                        currentChartIndex.value - 1
                    } else {
                        2
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(y = -240.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Chart",
                    tint = DarkBlue,
                    modifier = Modifier.size(50.dp)
                )
            }
            AnimatedVisibility(
                visible = currentChartIndex.value < 2,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 450.dp)
                    .align(Alignment.BottomCenter)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    items(classificationPercentages.entries.sortedByDescending { it.value }) { entry ->
                        ClassificationItem(classification = entry.key, total = entry.value.toDouble())
                    }
                }
            }
            AnimatedVisibility(
                visible = currentChartIndex.value == 2,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 450.dp)
                    .align(Alignment.BottomCenter)

            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(monthAll) { expense ->
                        MonthlyStatistic(
                            monthly = expense?.monthly.toString(),
                            total = expense?.monthlyExpense!!.toDouble(),
                            viewModel = bankViewModel
                        )
                    }
                }
            }


        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Display charts based on the index
            when (currentChartIndex.value) {
                0 -> PieChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 20.dp)
                        .offset(y = -50.dp)
                        .align(Alignment.Center),
                    pieChartData = pieChartData,
                    pieChartConfig = pieChartConfig
                )

                1 -> PieChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 20.dp)
                        .offset(y = -50.dp)
                        .align(Alignment.Center),
                    pieChartData = donutChartData,
                    pieChartConfig = pieChartConfig
                )

                2 -> Chart(data = expenseMonthMap, maxValue = maxValueZero.toInt(), viewModel)
            }

            // Navigation buttons
            Button(
                onClick = {
                    currentChartIndex.value = (currentChartIndex.value + 1) % 3
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = -240.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Next Chart",
                    tint = DarkBlue,
                    modifier = Modifier.size(50.dp)
                )
            }

            Button(
                onClick = {
                    currentChartIndex.value = if (currentChartIndex.value > 0) {
                        currentChartIndex.value - 1
                    } else {
                        2
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(y = -240.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Chart",
                    tint = DarkBlue,
                    modifier = Modifier.size(50.dp)
                )
            }

            // AnimatedVisibility for LazyVerticalGrid
            AnimatedVisibility(
                visible = currentChartIndex.value < 2,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 550.dp)
                    .align(Alignment.BottomCenter)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(classificationPercentages.entries.sortedByDescending { it.value }) { entry ->
                        ClassificationItem(classification = entry.key, total = entry.value.toDouble())
                    }
                }
            }
            AnimatedVisibility(
                visible = currentChartIndex.value == 2,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 550.dp)
                    .align(Alignment.BottomCenter)
            ) {
                if(monthAll.size == 0){
                    Text(
                        "Aqui ficará seus gastos mensais dos ultimos 5 meses!!",
                        modifier = Modifier
                            .padding(bottom = 120.dp)
                            .padding(horizontal = 40.dp)
                            .border(1.dp,  DarkBlue, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                        ,)
                }else{

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(monthAll) { expense ->
                            MonthlyStatistic(
                                monthly = expense?.monthly.toString(),
                                total = expense?.monthlyExpense!!.toDouble(),
                                viewModel = bankViewModel
                            )
                    }
                    }
                }
            }

        }
    }
}

@Composable
fun ClassificationItem(classification: String, total: Double) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    if(screenWidth < 400) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
                .padding(horizontal = 14.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(getColorForClassification(classification = classification))
                .height(70.dp)
                .padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = classification,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.White
            )
            Text(
                text = "%.2f".format(total) + "%",
                color = Color.White,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
                .padding(horizontal = 14.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(getColorForClassification(classification = classification))
                .height(70.dp)
                .padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = classification,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.White
            )
            Text(
                text = "%.2f".format(total) + "%",
                color = Color.White,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
fun MonthlyStatistic(monthly: String, total: Double, viewModel: BankViewModel) {
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
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 34.dp)
            .padding(horizontal = 14.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(DarkBlue)
            .height(70.dp)
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = monthly, color = Color.White, modifier = Modifier.padding(5.dp))
        Text(text = currencyFormat.format(total), color = Color.White)
    }

}
@Composable
fun getColorForClassification(classification: String): Color {
    return when (classification) {
        stringResource(id = R.string.transporte) -> Color(0xFF1E1A96)
        stringResource(id = R.string.esporte) -> Color(0xFFD96125)
        stringResource(id = R.string.lazer) -> Color(0Xff4A0F57)
        stringResource(id = R.string.servicos) -> Color(0xFFC2D507)
        stringResource(id = R.string.contas) -> Color(0xFF140708)
        stringResource(id = R.string.reserva_de_emergencia) -> Color(0xFFA0000A)
        stringResource(id = R.string.mercado) -> Color(0xFF01579B)
        stringResource(id = R.string.saude) -> Color(0xFFF53844)
        stringResource(id = R.string.investimento) -> Color(0xFFECC414)
        stringResource(id = R.string.animal_de_estimacao) -> Color(0xFF179620)
        stringResource(id = R.string.viagens) -> Color(0xFF1976D2)
        stringResource(id = R.string.educacao) -> Color(0xFF9E9797)
        stringResource(id = R.string.outros) -> Color(0xFFDDDDDD)
        else -> Color.Gray
    }
}
