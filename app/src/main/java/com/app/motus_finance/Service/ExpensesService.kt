package com.app.motus_finance.Service

import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses

class ExpensesService(private val repositoriesExpenses: RepositoriesExpenses) {
    suspend fun insertExpenses(expensesDTO: ExpensesDTO) : Boolean{
        return try {
            repositoriesExpenses.insertExpenses(expensesDTO)
            true
        }catch (e: Exception){
            false
        }
    }
}