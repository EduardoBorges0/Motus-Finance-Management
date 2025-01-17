package com.app.motus_finance.Service

import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses
import kotlin.math.exp

class BankService(private val repositoriesBank: RepositoriesBank) {
    suspend fun insertBank(bankDTO: BankDTO): Boolean {
        return try {
                repositoriesBank.insertBank(bankDTO)
                true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    suspend fun updateBalance(
        bankDTO: BankDTO,
        expensesDTO: ExpensesDTO
    ): BankDTO {
        val currentBalance = bankDTO.balance ?: 0.0 // Saldo atual padrão 0
        val transactionValue = expensesDTO.value ?: 0.0 // Valor da transação padrão 0

        val newBalance = when (expensesDTO.spentOrReceived) {
            "Spent" -> currentBalance - transactionValue // Subtrair o gasto
            "Received" -> currentBalance + transactionValue // Adicionar o recebido
            else -> currentBalance // Sem alteração se for inválido
        }

        // Atualiza o saldo no banco de dados
        repositoriesBank.updateBalance(
            bankId = expensesDTO.bankId ?: 0,
            newBalance = newBalance
        )

        // Atualiza o objeto bankDTO com o novo saldo
        return bankDTO.copy(balance = newBalance)
    }

}
