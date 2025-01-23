package com.app.motus_finance.Models.Repositories

import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.Entities.Expenses

class RepositoriesExpenses(private val expensesDAO: ExpensesDAO) {
    suspend fun insertExpenses(expenses: Expenses){
        return expensesDAO.insertExpenses(expenses)
    }
    suspend fun getTotalExpenses(fixedOrVariable: String, bankId: Int) : Double?{
        return expensesDAO.getTotalExpenses(fixedOrVariable, bankId)
    }
}