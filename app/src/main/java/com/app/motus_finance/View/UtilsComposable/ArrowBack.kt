package com.app.motus_finance.View.UtilsComposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus_finance.View.NavBottoms.HomeScreen.ui.theme.MainColor
import com.app.motus_finance.View.UtilsComposable.ui.theme.Motus_FINANCETheme

@Composable
fun ArrowBack(navController: NavController){
    Box(modifier = Modifier
        .padding(top = 60.dp)
        .padding(horizontal = 10.dp)
    ){
        Button(onClick = {
            navController.popBackStack()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MainColor
        )) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "arrowBack",
            )
        }
    }
}