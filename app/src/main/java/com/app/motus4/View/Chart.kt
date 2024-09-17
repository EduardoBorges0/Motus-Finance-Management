package com.app.motus4.View


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.simplemoney.ui.theme.DarkBlue

@Composable
fun Chart(
    data: Map<Float, String>,
    maxValue: Int,
    viewModel: ExpenseViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val context = LocalContext.current
    // BarGraph Dimensions
    val barGraphWidth by remember { mutableStateOf(20.dp) }
    // Scale Dimensions
    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }
    if (screenWidth < 400) {
        val barGraphHeight by remember { mutableStateOf(250.dp) }


        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize()
                .offset(y = -130.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barGraphHeight),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                // scale Y-Axis
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleYAxisWidth),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = maxValue.toString())
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = (maxValue / 2).toString())
                        Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight().padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = "0")
                    }


                }

                // Y-Axis Line
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleLineWidth)
                        .background(Color.Black)
                )

                // graph
                data.forEach { (value, label) ->
                    Box(
                        modifier = Modifier
                            .padding(start = barGraphWidth, bottom = 5.dp)
                            .padding(horizontal = 2.dp)
                            .clip(CircleShape)
                            .width(barGraphWidth)
                            .height((value / maxValue) * barGraphHeight) // Adjust height based on maxValue
                            .background(DarkBlue)
                            .clickable {
                                Toast
                                    .makeText(context, value.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    )
                }
            }

            // X-Axis Line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scaleLineWidth)
                    .background(Color.Black)
            )
            val monthAll by viewModel.getAllMonthlyExpense().observeAsState(emptyList())

            // Scale X-Axis
            Row(
                modifier = Modifier
                    .padding(start = scaleYAxisWidth + barGraphWidth + scaleLineWidth)
                    .offset(x = -2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(barGraphWidth)

            ) {
                data.values.forEach {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = it.take(2),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }else{
        val barGraphHeight by remember { mutableStateOf(350.dp) }

        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize()
                .offset(y = -120.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barGraphHeight),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                // scale Y-Axis
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleYAxisWidth),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = maxValue.toString())
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = (maxValue / 2).toString())
                        Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight().padding(bottom = 5.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = "0")
                    }


                }

                // Y-Axis Line
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleLineWidth)
                        .background(Color.Black)
                )

                // graph
                data.forEach { (value, label) ->
                    Box(
                        modifier = Modifier
                            .padding(start = barGraphWidth, bottom = 5.dp)
                            .padding(horizontal = 2.dp)
                            .clip(CircleShape)
                            .width(barGraphWidth)
                            .height((value / maxValue) * barGraphHeight) // Adjust height based on maxValue
                            .background(DarkBlue)
                            .clickable {
                                Toast
                                    .makeText(context, value.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    )
                }
            }

            // X-Axis Line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scaleLineWidth)
                    .background(Color.Black)
            )
            val monthAll by viewModel.getAllMonthlyExpense().observeAsState(emptyList())

            // Scale X-Axis
            Row(
                modifier = Modifier
                    .padding(start = scaleYAxisWidth + barGraphWidth + scaleLineWidth)
                    .offset(x = -2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(barGraphWidth)

            ) {
                data.values.forEach {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = it.take(2),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}