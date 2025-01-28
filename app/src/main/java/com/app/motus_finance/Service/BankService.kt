package com.app.motus_finance.Service

import androidx.lifecycle.LiveData
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Entities.Banks
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.Repositories.RepositoriesDueDates
import com.app.motus_finance.UtilityClass.DateUtils.stringToLocalDate
import java.time.LocalDate

class BankService(private val repositoriesBank: RepositoriesBank) {
    suspend fun insertBank(bankDTO: BankDTO): Boolean {
        return try {
                repositoriesBank.insertBank(bankDTO.toEntity())
                true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    fun getAllBanks(): LiveData<List<Banks>> {
        return repositoriesBank.getAllBanks()
    }
    suspend fun updateSum(bankId: Int, sum: Double){
        return repositoriesBank.updateSum(bankId, sum)
    }

    suspend fun updateDatePlusMonth(date: String, id: Int){
        return repositoriesBank.updateDatePlusMonth(date, id)
    }


    suspend fun deleteBanks(id: Int){
        return repositoriesBank.deleteBanks(id)
    }
    suspend fun updateBalance(
        bankId: Int,
        expensesDTO: ExpensesDTO
    ) {
        val currentBalance = repositoriesBank.getBalanceById(bankId)
        val transactionValue = expensesDTO.toEntity().value ?: 0.0

        val newBalance = when (expensesDTO.toEntity().spentOrReceived) {
            "Spent" -> currentBalance - transactionValue
            "Received" -> currentBalance + transactionValue
            else -> currentBalance
        }

        repositoriesBank.updateBalance(
            bankId = bankId,
            newBalance = newBalance
        )
    }


    suspend fun updateBankDate(bankId: Int, bankDTO: BankDTO): String {
        val date = stringToLocalDate(bankDTO.date.toString())
        return if (date == LocalDate.now()) {
            val newDate = date.plusMonths(1)
            repositoriesBank.updateBankDate(bankId, newDate.toString())
            newDate.toString()
        } else {
            bankDTO.toEntity().date.toString()
        }
    }
}
