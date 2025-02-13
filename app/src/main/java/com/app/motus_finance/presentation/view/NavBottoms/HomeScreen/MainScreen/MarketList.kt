package com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.MainScreen

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.app.motus_finance.data.models.Market
import com.app.motus_finance.presentation.UtilityClass.DateUtils
import com.app.motus_finance.presentation.view.ui.theme.MainColor
import com.app.motus_finance.presentation.viewmodel.MarketViewModel

@Composable
fun MarketList(market: Market, bankViewModel: MarketViewModel) {
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Color(parseColor(market.color))
            )
    ) {
        IconButton(onClick = {
            bankViewModel.deleteBanks(market.id)
        },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(bottom = 130.dp, end = 30.dp)) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "icons",
                tint = Color.White
            )
        }
        Text(
            text = DateUtils.currencyFormat(market.balance?.toDouble() ?: 0.0),
            modifier = Modifier
                .padding(26.dp),
            color = Color.White,
            fontSize = 18.sp
        )
        Text(
            text = market.date.toString(),
            modifier = Modifier
                .padding(26.dp)
                .align(Alignment.Center),
            color = Color.White,
            fontSize = 18.sp
        )
        AsyncImage(
            model = market.img,
            contentDescription = "icon bank",
            modifier = Modifier
                .size(60.dp)
                .padding(top = 10.dp)
                .align(Alignment.TopCenter)
        )
        Text(
            text = market.name.toString(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 56.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(200.dp)
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor
            )
        ) {
            Text("Adicionar compra")
        }
    }
}
