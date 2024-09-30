package com.app.motus4.View

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus4.R
import com.app.simplemoney.ui.theme.DarkBlue
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var auth : FirebaseAuth

@Composable
fun RevealImageAnimation(navController: NavController) {
    // Controla a progressão da animação (de 0f a 1f)
    val progress = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    auth = Firebase.auth

    // Executa a animação quando o Composable é exibido
    LaunchedEffect(Unit) {
        // Anima a revelação (escala vertical de 0 a 1)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500, // Duração da revelação
                easing = LinearOutSlowInEasing
            )
        )
        // Anima a opacidade (de transparente para visível)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000, // Duração da opacidade
                easing = LinearOutSlowInEasing
            )
        )

        // Após a animação terminar, navega para a próxima tela (main)

            navController.navigate("main") {
                popUpTo("splash") { inclusive = true } // Remove a SplashScreen da pilha de navegação
            }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Substitua pelo seu recurso de imagem
            contentDescription = "Revealed Image",
            modifier = Modifier
                .size(100.dp)
                .alpha(alpha.value)
                .graphicsLayer {
                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 0.5f)
                    scaleY = progress.value // Crescimento vertical da imagem
                }
        )
    }
}
