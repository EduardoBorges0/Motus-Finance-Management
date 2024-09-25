package com.app.motus4.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> get() = _showDialog

    fun signUpFireBase(navController: NavController, email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                navController.navigate("main"){
                    popUpTo("register") { inclusive = true }
                    popUpTo("registerEmailAndPassword") { inclusive = true } // Remove a SplashScreen da pilha de navegação
                }
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.d("ERRO", "DEU ERRO NO CADASTRO, CADASTRO IGUAL ${e}")
                _showDialog.value = true
            }
        }
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
}