package com.app.motus_finance.domain.usecases

import android.util.Log
import com.app.motus_finance.domain.dto.ExpensesDTO
import com.app.motus_finance.domain.dto.toEntity
import com.app.motus_finance.data.repositories.RepositoriesExpenses
import com.app.motus_finance.data.repositories.RepositoriesMarket
import javax.inject.Inject

class ExpensesUseCases @Inject constructor(private val repositoriesExpenses: RepositoriesExpenses, private val repositoriesBank: RepositoriesMarket) {
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