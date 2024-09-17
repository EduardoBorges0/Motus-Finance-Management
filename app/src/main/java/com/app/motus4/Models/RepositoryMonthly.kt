package com.app.motus4.Models

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.app.motus4.Models.Room.DaoMonthlyExpense
import com.app.motus4.Models.Room.DataClass.MonthlyExpense

class RepositoryMonthly(private val daoMonthlyExpense: DaoMonthlyExpense) {
    suspend fun insertMonthlyExpense(monthlyExpense: MonthlyExpense){
        return daoMonthlyExpense.insertMonthlyExpense(monthlyExpense)
    }
    suspend fun getMonthlyExpense(monthShortName: String, year: Int): MonthlyExpense? = daoMonthlyExpense.getMonthlyExpense(monthShortName, year)

    suspend fun removeDuplicateMonthlyExpenses() = daoMonthlyExpense.removeDuplicateMonthlyExpenses()

    fun getItemCount(): Int = daoMonthlyExpense.getItemCount()

    // Excluir o item mais antigo (com menor id, por exemplo)
    fun deleteOldestItem() = daoMonthlyExpense.deleteOldestItem()

     fun getAllMonthlyExpense() : LiveData<List<MonthlyExpense?>>{
        return daoMonthlyExpense.getAllMonthlyExpense()
    }
}