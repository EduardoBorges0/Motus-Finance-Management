package com.app.motus_finance.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.domain.usecases.GraphicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphicsViewModel @Inject constructor(private val graphicsUseCases: GraphicsUseCase) : ViewModel() {

    fun insertGraphics(){
        viewModelScope.launch {
            graphicsUseCases.insertGraphics()
        }
    }
    fun getAllSpendingRatings(){
         viewModelScope.launch {
             graphicsUseCases.insertGraphics()
         }
    }
}