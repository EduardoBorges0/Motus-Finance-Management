package com.app.motus_finance.View.NavBottoms.HomeScreen

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.motus_finance.Models.Entities.Banks
import com.app.motus_finance.ViewModel.BanksViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ListBanks(banks: Banks, bankViewModel: BanksViewModel) {
    val currency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Color(parseColor(banks.color))
            )
    ) {
        IconButton(onClick = {
            bankViewModel.deleteBanks(banks.id)
        },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(bottom = 85.dp, end = 30.dp)) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "icons",
                tint = Color.White
            )
        }
        Text(
            text = currency.format(banks.balance).toString(),
            modifier = Modifier.padding(20.dp),
            color = Color.White,
            fontSize = 18.sp
        )
        Text(
            text = banks.date.toString(),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter),
            color = Color.White,
            fontSize = 18.sp
        )
        AsyncImage(
            model = banks.img,
            contentDescription = "icon bank",
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopCenter)
        )
        Text(
            text = banks.name.toString(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 56.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }

}
