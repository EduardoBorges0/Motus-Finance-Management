package com.app.motus_finance.Service

import android.util.Log
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Repositories.RepositoriesMarket
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses

class ExpensesService(private val repositoriesExpenses: RepositoriesExpenses, private val repositoriesBank: RepositoriesMarket) {
    suspend fun insertExpenses(expensesDTO: ExpensesDTO) : Boolean{
        return try {
            repositoriesExpenses.insertExpenses(expensesDTO.toEntity())
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun updateBanksForDueDate(
        id: Int,
        ) {
            val getAllExpenses = repositoriesExpenses.sumBalance(id)
            Log.d("SOMA TOTQAL", "SOMANDO $getAllExpenses")
            repositoriesBank.updateBalance(id, getAllExpenses)
    }
}