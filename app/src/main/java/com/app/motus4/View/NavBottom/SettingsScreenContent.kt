package com.app.simplemoney8.View.NavBottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.motus4.R

@Composable
fun SettingsScreenContent(navController: NavController){
   Box (
       modifier = Modifier.fillMaxSize()
   ){
       Box (modifier = Modifier
           .padding(horizontal = 15.dp)
           .clip(RoundedCornerShape(8.dp))
           .background(DarkBlue)
           .fillMaxWidth()
           .height(60.dp)
           .align(Alignment.Center)
           .clickable {
               navController.navigate("changeLanguage")
           }){
           Text(text = stringResource(id = R.string.mudar_o_idioma),
               color = Color.White,
               modifier = Modifier
               .align(Alignment.Center))
       }
   }
}