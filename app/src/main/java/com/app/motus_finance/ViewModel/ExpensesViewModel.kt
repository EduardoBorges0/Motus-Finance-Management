package com.app.motus_finance.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Service.ExpensesService
import kotlinx.coroutines.launch
import kotlin.math.exp

class ExpensesViewModel(private val expensesService: ExpensesService) : ViewModel() {
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

        return expensesService.insertExpenses(expensesDTO)
    }

    suspend fun updateBanksForDueDate(id: Int) : Double{
             return expensesService.updateBanksForDueDate("Variable", id)
    }
}