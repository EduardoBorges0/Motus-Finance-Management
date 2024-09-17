package com.app.motus2.View

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus4.Models.Room.DataClass.ModelsLanguage
import com.app.motus4.R
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.motus4.setAppLocale


@Composable
fun ChangeLanguageContent(navController: NavController, activity: Activity, viewModel: BankViewModel) {
    var lang  by remember {
        mutableStateOf("")
    }
    val listLanguage = listOf(
        stringResource(id = R.string.portugues),
        stringResource(id = R.string.ingles),
        stringResource(id = R.string.espanhol)
    )

    val languageCodes = listOf("pt", "en", "es")

    Button(
        onClick = { navController.popBackStack() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = DarkBlue
        ),
        modifier = Modifier.padding(top = 30.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "return"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Itera pelos idiomas e exibe cada um em um botão
        listLanguage.forEachIndexed { index, language ->
            Text(
                text = language,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkBlue)
                    .fillMaxWidth()
                    .padding(top = 19.dp)
                    .clickable {
                        lang = languageCodes[index]
                        val modelsLang = ModelsLanguage(0, lang)
                        viewModel.insertLanguage(modelsLang)
                        setAppLocale(activity, lang)
                        navController.navigate("home")
                    },
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
