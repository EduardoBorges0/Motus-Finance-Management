package com.app.motus_finance.data.repositories

import com.app.motus_finance.data.dao.ExpensesDAO
import com.app.motus_finance.data.models.Expenses
import com.app.motus_finance.data.models.HighestSpending

class RepositoriesExpenses(private val expensesDAO: ExpensesDAO) {
    suspend fun insertExpenses(expenses: Expenses){
        return expensesDAO.insertExpenses(expenses)
    }
    suspend fun getTotalExpenses(fixedOrVariable: String, bankId: Int) : Double?{
        return expensesDAO.getTotalExpenses(fixedOrVariable, bankId)
    }
    suspend fun getHighestSpendingRating(): List<HighestSpending>?{
        return expensesDAO.getAllSpendingRatings()
    }
    suspend fun deleteVariables(bankId: Int){
        return expensesDAO.deleteVariables(bankId)
    }
    suspend fun sumBalance(id: Int) : Double{
        return  expensesDAO.sumBalance(id)
    }

}