package com.app.motus_finance.Models.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.motus_finance.Models.DTO.ExpensesDTO

@Dao
interface ExpensesDAO {

    @Insert
    suspend fun insertExpenses(expensesDTO: ExpensesDTO)

    @Query("SELECT SUM(value) FROM table_expenses WHERE fixedOrVariable = :fixedOrVariable AND bankId = :bankId")
    fun getTotalExpenses(fixedOrVariable: String, bankId: Int): Double?


}