package com.app.motus_finance.Models.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.app.motus_finance.Models.DTO.ExpensesDTO

@Dao
interface ExpensesDAO {

    @Insert
    suspend fun insertExpenses(expensesDTO: ExpensesDTO)
}