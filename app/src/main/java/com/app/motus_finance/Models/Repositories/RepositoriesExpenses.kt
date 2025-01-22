package com.app.motus_finance.Models.Repositories

import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DTO.ExpensesDTO

class RepositoriesExpenses(private val expensesDAO: ExpensesDAO) {
    suspend fun insertExpenses(expensesDTO: ExpensesDTO){
        return expensesDAO.insertExpenses(expensesDTO)
    }
    suspend fun getTotalExpenses(fixedOrVariable: String, bankId: Int) : Double?{
        return expensesDAO.getTotalExpenses(fixedOrVariable, bankId)
    }
}