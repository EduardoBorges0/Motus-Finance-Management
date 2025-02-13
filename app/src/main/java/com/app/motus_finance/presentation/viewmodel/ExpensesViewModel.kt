package com.app.motus_finance.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.motus_finance.domain.dto.ExpensesDTO
import com.app.motus_finance.domain.usecases.ExpensesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(private val expensesUseCases: ExpensesUseCases) : ViewModel() {
    var selectedDate by mutableStateOf("Selecionar Data")

    suspend fun insertExpenses(expensesDTO: ExpensesDTO) : Boolean{
        if (expensesDTO.expenseDescription == null||
            expensesDTO.readyForDeletion == null||
            expensesDTO.date == null||
            expensesDTO.bankId == null||
            expensesDTO.value == null||
            expensesDTO.classification == null||
            expensesDTO.fixedOrVariable == null||
            expensesDTO.spentOrReceived == null
        ) return false

        return expensesUseCases.insertExpenses(expensesDTO)
    }
    suspend fun updateBanksForDueDate(id: Int){
        return expensesUseCases.updateBanksForDueDate(id)
    }
}