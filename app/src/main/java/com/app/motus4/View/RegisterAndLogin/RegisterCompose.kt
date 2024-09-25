package com.app.motus4.View.RegisterAndLogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus4.R
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.simplemoney8.customFontFamily

@Composable
fun RegisterComposable(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(DarkBlue)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mainlogo),
                contentDescription = "Ícone de carteira",
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.Center)
            )
        }

        // Use Spacer to create space between the logo and buttons
        Spacer(modifier = Modifier.height(16.dp))

        // Align the buttons in the center horizontally
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 100.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate("registerEmailAndPassword")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cadastrar com Email e Senha", fontFamily = customFontFamily)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons
            Button(
                onClick = {
                    // Add your onClick logic here
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cadastrar com o Google", fontFamily = customFontFamily)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons

            Button(
                onClick = {
                    navController.navigate("loginCompose")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Já tenho Conta", fontFamily = customFontFamily)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons

            Button(
                onClick = {
                    navController.navigate("main"){
                        popUpTo("register") { inclusive = true }
                         }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Quero entrar sem conta", fontFamily = customFontFamily)
            }

        }
    }
}
