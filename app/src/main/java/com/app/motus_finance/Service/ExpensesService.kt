package com.app.motus_finance.Service

import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses
import com.app.motus_finance.UtilityClass.DateUtils
import java.time.LocalDate

class ExpensesService(private val repositoriesExpenses: RepositoriesExpenses) {
    suspend fun insertExpenses(expensesDTO: ExpensesDTO) : Boolean{
        return try {
            repositoriesExpenses.insertExpenses(expensesDTO.toEntity())
            true
        }catch (e: Exception){
            false
        }
    }
    suspend fun updateBanksForDueDate(
        expensesDTO: ExpensesDTO,
        bankDTO: BankDTO,
        ) : Double?{
        val bankDate = DateUtils.stringToLocalDate(bankDTO.date.toString())
        var sum = 0.0
        if(bankDate == LocalDate.now()){
              val getAllExpenses = expensesDTO.bankId?.let { repositoriesExpenses.getTotalExpenses(expensesDTO.fixedOrVariable.toString(), it) }
              sum = getAllExpenses ?: 0.0
         }
        return sum
    }
}