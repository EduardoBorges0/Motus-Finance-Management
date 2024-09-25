package com.app.motus4.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> get() = _showDialog

    fun signInFireBase(navController: NavController, email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                navController.navigate("main"){
                    popUpTo("register") { inclusive = true }
                    popUpTo("loginCompose") { inclusive = true } // Remove a SplashScreen da pilha de navegação
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d("ERRO", "DEU ERRO NO Login, LOGIN IGUAL ${e}")
                _showDialog.value = true
            }
        }
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
}