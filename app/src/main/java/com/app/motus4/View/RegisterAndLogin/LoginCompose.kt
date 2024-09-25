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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.motus4.R
import com.app.motus4.ViewModels.LoginViewModel
import com.app.motus4.ViewModels.RegisterViewModel
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.simplemoney8.customFontFamily

@Composable
fun LoginCompose(navController: NavController){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val viewModel: LoginViewModel = viewModel() // Obtenha a ViewModel corretamente

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
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "return")
            }
            Image(
                painter = painterResource(id = R.drawable.mainlogo),
                contentDescription = "Ícone de carteira",
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.Center)
            )
        }

        // Use Spacer to create space between the logo and buttons

        // Align the buttons in the center horizontally
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 100.dp)
        ) {
            Text(
                text = "Login", modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 31.dp),
                style = MaterialTheme.typography.headlineMedium.copy(),
                fontStyle = FontStyle(R.font.montserratregular)
            )
            Spacer(modifier = Modifier.height(36.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email")},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(350.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp)

            )
            Spacer(modifier = Modifier.height(36.dp)) // Add space between buttons
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password")},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(350.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp)

            )
            Spacer(modifier = Modifier.weight(1f)) // Add space between buttons

            Button(
                onClick = {
                    viewModel.signInFireBase(navController, email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cadastrar", fontFamily = customFontFamily)
            }
        }
        if (viewModel.showDialog.value) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissDialog() },
                title = { Text(text = "Validation Error") },
                text = { Text(text = "Email dont exists.") },
                confirmButton = {
                    TextButton(onClick = { navController.navigate("loginCompose") }) {
                        Text(text = "Ir para o cadastro", color = DarkBlue)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.dismissDialog() }) {
                        Text(text = "Cancelar", color = DarkBlue)
                    }
                }
            )
        }
    }
}