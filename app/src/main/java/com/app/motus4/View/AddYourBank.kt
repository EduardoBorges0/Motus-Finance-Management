package com.app.motus2.View


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.simplemoney.ui.theme.DarkBlue

import com.app.motus4.R
import com.app.simplemoney.ui.theme.BlueSonic
import com.app.simplemoney.ui.theme.DarkExpense
import com.app.simplemoney.ui.theme.LightDarkExpense
import com.app.simplemoney.ui.theme.LightOrangeExpense
import com.app.simplemoney.ui.theme.OrangeExpense
import com.app.simplemoney.ui.theme.PurpleExpense
import com.app.simplemoney.ui.theme.RedExpense


@Composable
fun AddYourBankComposable(
    nameOfBank: String?,
    viewModel: BankViewModel,
    balance: Double?,
    creditOrDebit: String?,
    date: String?,
    img: String?,
    navController: NavController
) {
    Log.d("AddImageContent", "Navigating with image URI: $img")

    val bankList = listOf(
        BankInfo("PE", "#9D62D9", img.toString(), PurpleExpense, "#820BD0"),
        BankInfo("BB", "#548DE3", img.toString(), BlueSonic, "#3156A7"),
        BankInfo("LO", "#F29979", img.toString(), LightOrangeExpense, "#FF7B01"),
        BankInfo("LD", "#232323", img.toString(), LightDarkExpense, "#21C25E"),
        BankInfo("RE", "#CC2A4A", img.toString(), RedExpense, "#CC092F"),
        BankInfo("DE", "#111112", img.toString(), DarkExpense, "#252525"),
        BankInfo("OE", "#FE6200", img.toString(), OrangeExpense, "#2E3191"),


    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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

        bankList.forEach { bankInfo ->
            BankBox(
                bankInfo = bankInfo,
                creditOrDebit = creditOrDebit,
                balance = balance,
                date = date,
                nameOfBank = nameOfBank,
                onClick = {
                    viewModel.insertBank(
                        bankInfo.name,
                        bankInfo.color,
                        bankInfo.imageRes.toString(),
                        creditOrDebit,
                        balance,
                        bankInfo.colorSpentsOrReceived,
                        date,
                        nameOfBank
                    )
                    navController.navigate("home")
                }
            )
        }
    }
}

@Composable
fun BankBox(
    bankInfo: BankInfo,
    creditOrDebit: String?,
    balance: Double?,
    date: String?,
    nameOfBank: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .padding(top = 80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bankInfo.backgroundColor)
            .height(170.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = bankInfo.imageRes,
            contentDescription = bankInfo.name,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
                .size(60.dp)
        )
    }
}

data class BankInfo(
    val name: String,
    val color: String,
    val imageRes: String,
    val backgroundColor: Color,
    val colorSpentsOrReceived: String
)
