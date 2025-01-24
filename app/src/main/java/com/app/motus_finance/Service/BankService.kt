package com.app.motus_finance.Service

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

    suspend fun deleteBanks(id: Int){
        return repositoriesBank.deleteBanks(id)
    }
    suspend fun updateBalance(
        bankDTO: BankDTO,
        expensesDTO: ExpensesDTO
    ): Banks {
        val currentBalance = bankDTO.toEntity().balance ?: 0.0 // Saldo atual padrão 0
        val transactionValue = expensesDTO.toEntity().value ?: 0.0 // Valor da transação padrão 0

        val newBalance = when (expensesDTO.toEntity().spentOrReceived) {
            "Spent" -> currentBalance - transactionValue // Subtrair o gasto
            "Received" -> currentBalance + transactionValue // Adicionar o recebido
            else -> currentBalance // Sem alteração se for inválido
        }

        // Atualiza o saldo no banco de dados
        repositoriesBank.updateBalance(
            bankId = expensesDTO.toEntity().bankId ?: 0,
            newBalance = newBalance
        )

        // Atualiza o objeto bankDTO com o novo saldo
        return bankDTO.toEntity().copy(balance = newBalance)
    }


    suspend fun updateBankDate(bankId: Int, bankDTO: BankDTO): String {
        val date = stringToLocalDate(bankDTO.date.toString())
        return if (date == LocalDate.now()) {
            val newDate = date.plusMonths(1)
            repositoriesBank.updateBankDate(bankId, newDate.toString())
            newDate.toString() // Retorna a nova data
        } else {
            bankDTO.toEntity().date.toString()
        }
    }


}
