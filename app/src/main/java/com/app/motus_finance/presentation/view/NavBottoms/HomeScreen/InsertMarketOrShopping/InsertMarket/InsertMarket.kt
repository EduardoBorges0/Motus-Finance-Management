package com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertMarketOrShopping.InsertMarket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.motus_finance.domain.dto.MarketDTO
import com.app.motus_finance.presentation.view.UtilsComposable.ArrowBack
import com.app.motus_finance.presentation.viewmodel.MarketViewModel

@Composable
fun InsertMarket(name: String?, marketViewModel: MarketViewModel, navController: NavController) {
    ArrowBack(navController = navController)

    Box(modifier = Modifier.fillMaxSize().padding(top = 120.dp)){
        LazyColumn {
            items(marketViewModel.getMarketList(name)){ marketList ->
                Box(modifier = Modifier.fillMaxSize().padding(top = 20.dp, bottom = 20.dp)) {
                    MarketListComposable(marketList,
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                        marketViewModel = marketViewModel,
                        navController = navController
                    )
                }
            }
        }
    }

}

@Composable
fun MarketListComposable(
    insertMarketModel: InsertMarketModel,
    modifier: Modifier,
    marketViewModel: MarketViewModel,
    navController: NavController
){
    val heigthSize = LocalConfiguration.current.screenHeightDp
    val widthSize = LocalConfiguration.current.screenWidthDp
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(insertMarketModel.backgroundColor))
            .width(widthSize.dp / 1.4f)
            .height(heigthSize.dp / 6)
            .clickable {
                marketViewModel.insertBank(
                    MarketDTO(
                        name = insertMarketModel.name,
                        color = insertMarketModel.backgroundColor.toString(),
                        img = insertMarketModel.img,
                        balance = insertMarketModel.balance,
                        colorSpentsOrReceived = insertMarketModel.colorSpentsOrReceived.toString(),
                        date = insertMarketModel.date,
                        sum = insertMarketModel.sum
                    )
                )
                navController.navigate("main")
            }
    ){
        AsyncImage(
            model = insertMarketModel.img,
            contentDescription = "marketImage",
            modifier = Modifier
                .size(widthSize.dp / 7.3f)
                .align(Alignment.TopCenter)
                .padding(top = 12.dp)
        )
        Text(
            insertMarketModel.name.toString(),
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 66.dp, bottom = 20.dp)
        )
    }
}
data class InsertMarketModel(
    val name: String?,
    val backgroundColor: Long,
    val img: Int?,
    val balance: Double?,
    val colorSpentsOrReceived: Long,
    val date: String?,
    val sum: Double?
)