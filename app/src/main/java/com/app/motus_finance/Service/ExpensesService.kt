package com.app.motus_finance.Service

import android.util.Log
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.Repositories.RepositoriesExpenses
import com.app.motus_finance.UtilityClass.DateUtils
import java.time.LocalDate

class ExpensesService(private val repositoriesExpenses: RepositoriesExpenses, private val repositoriesBank: RepositoriesBank) {
    suspend fun insertExpenses(expensesDTO: ExpensesDTO) : Boolean{
        return try {
            repositoriesExpenses.insertExpenses(expensesDTO.toEntity())
            true
        }catch (e: Exception){
            false
        }
    }
    suspend fun updateBanksForDueDate(
        fixedOrVariable: String,
        id: Int,
        ) : Double{
        val date = repositoriesBank.getDatesById(id)
        val bankDate = DateUtils.stringToLocalDate(date)
        var sum = 0.0
        if(bankDate == LocalDate.now()){
              val getAllExpenses = repositoriesExpenses.getTotalExpenses(fixedOrVariable, id)
              Log.d("SOMA TOTAL", "ESSA É A SOMA: $getAllExpenses")
              repositoriesBank.updateSum(id, getAllExpenses ?: 0.0)
            sum = getAllExpenses ?: 0.0
            repositoriesExpenses.deleteVariables(id)
         }
        return sum
    }
}