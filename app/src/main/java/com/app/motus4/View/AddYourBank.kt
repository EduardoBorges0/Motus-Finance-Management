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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.simplemoney.ui.theme.DarkBlue

import com.app.motus4.R
import com.app.simplemoney.ui.theme.DarkExpense
import com.app.simplemoney.ui.theme.GreyC6
import com.app.simplemoney.ui.theme.LightDarkExpense
import com.app.simplemoney.ui.theme.LightOrangeExpense
import com.app.simplemoney.ui.theme.OrangeExpense
import com.app.simplemoney.ui.theme.PurpleExpense
import com.app.simplemoney.ui.theme.RedExpense
import com.app.simplemoney.ui.theme.RedSantander
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


private lateinit var auth: FirebaseAuth
@Composable
fun AddYourBankComposable(
    nameOfBank: String?,
    viewModel: BankViewModel,
    balance: Double?,
    creditOrDebit: String?,
    date: String?,
    navController: NavController
) {
    val bankList = listOf(
        BankInfo("PE", "#9D62D9", R.drawable.nubank, PurpleExpense, "#820BD0"),
        BankInfo("LD", "#232323", R.drawable.picpay, LightDarkExpense, "#21C25E"),
        BankInfo("Bradesco", "#F0022C", R.drawable.bradesco, RedExpense, "#CC092F"),
        BankInfo("Santander", "#FE0002", R.drawable.santander, RedSantander, "#FFFFFF"),
        BankInfo("c6", "#1F1F1F", R.drawable.c6, GreyC6, "#464646"),
        BankInfo("DE", "#111112", R.drawable.xp, DarkExpense, "#252525"),
        BankInfo("OE", "#FE6200", R.drawable.itau, OrangeExpense, "#2E3191")
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
    val imageRes: Int, // Alterado para Int para aceitar recursos do drawable
    val backgroundColor: Color,
    val colorSpentsOrReceived: String
)
