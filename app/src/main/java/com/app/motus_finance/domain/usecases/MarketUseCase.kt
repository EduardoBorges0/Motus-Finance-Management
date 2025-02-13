package com.app.motus_finance.domain.usecases

import androidx.lifecycle.LiveData
import com.app.motus_finance.data.models.Market
import com.app.motus_finance.data.repositories.RepositoriesMarket
import com.app.motus_finance.domain.dto.ExpensesDTO
import com.app.motus_finance.domain.dto.MarketDTO
import com.app.motus_finance.domain.dto.toEntity
import com.app.motus_finance.presentation.UtilityClass.DateUtils.stringToLocalDate
import java.time.LocalDate
import javax.inject.Inject

class MarketUseCase @Inject constructor(private val repositoriesBank: RepositoriesMarket) {
    suspend fun insertBank(marketDTO: MarketDTO): Boolean {
        return try {
                repositoriesBank.insertBank(marketDTO.toEntity())
                true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllBanks(): LiveData<List<Market>> {
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


    suspend fun updateBankDate(bankId: Int, marketDTO: MarketDTO): String {
        val date = stringToLocalDate(marketDTO.date.toString())
        return if (date == LocalDate.now()) {
            val newDate = date.plusMonths(1)
            repositoriesBank.updateBankDate(bankId, newDate.toString())
            newDate.toString()
        } else {
            marketDTO.toEntity().date.toString()
        }
    }
}
