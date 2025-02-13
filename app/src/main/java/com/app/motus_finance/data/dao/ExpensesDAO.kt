package com.app.motus_finance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.motus_finance.data.models.Expenses
import com.app.motus_finance.data.models.HighestSpending

@Dao
interface ExpensesDAO {

    @Insert
    suspend fun insertExpenses(expenses: Expenses)

    @Query("SELECT SUM(value) FROM table_expenses WHERE fixedOrVariable = :fixedOrVariable AND bankId = :bankId")
    fun getTotalExpenses(fixedOrVariable: String, bankId: Int): Double?

    @Query("SELECT classification, SUM(value) as total FROM table_expenses GROUP BY classification ORDER BY total DESC")
    suspend fun getAllSpendingRatings(): List<HighestSpending>

    @Query("SELECT SUM(value) FROM table_expenses WHERE bankId = :id")
    suspend fun sumBalance(id: Int) : Double



    @Query("DELETE FROM table_expenses WHERE fixedOrVariable = 'Variable' AND bankId= :bankId")
    suspend fun deleteVariables(bankId: Int)
}