package com.app.motus_finance.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.DTO.GraphicsDTO
import com.app.motus_finance.Models.Entities.HighestSpending
import com.app.motus_finance.Service.GraphicsService
import kotlinx.coroutines.launch

class GraphicsViewModel(private val service: GraphicsService) : ViewModel() {

    fun insertGraphics(){
        viewModelScope.launch {
            service.insertGraphics()
        }
    }
    fun getAllSpendingRatings(){
         viewModelScope.launch {
             service.insertGraphics()
         }
    }
}