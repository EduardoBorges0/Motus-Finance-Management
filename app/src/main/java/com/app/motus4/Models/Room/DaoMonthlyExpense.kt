package com.app.motus4.Models.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.motus4.Models.Room.DataClass.MonthlyExpense

@Dao
interface DaoMonthlyExpense {

    @Delete
    suspend fun deleteMonths(monthlyExpense: MonthlyExpense)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyExpense(monthlyExpense: MonthlyExpense)

    @Query("SELECT * FROM monthlyExpense")
     fun getAllMonthlyExpense() : LiveData<List<MonthlyExpense?>>

    @Query("SELECT * FROM monthlyExpense WHERE monthly = :monthShortName AND yearExpense = :year")
    suspend fun getMonthlyExpense(monthShortName: String, year: Int): MonthlyExpense?


    @Query("DELETE FROM monthlyExpense WHERE monthly = :monthly")
    suspend fun deleteMonthlyExpenseByMonth(monthly: String)

    @Query("SELECT COUNT(*) FROM monthlyExpense")
    fun getItemCount(): Int

    // Excluir o item mais antigo (com menor id, por exemplo)
    @Query("DELETE FROM monthlyExpense WHERE id = (SELECT id FROM monthlyExpense ORDER BY id ASC LIMIT 1)")
    fun deleteOldestItem()

    @Query("""
        DELETE FROM monthlyExpense 
    WHERE id NOT IN (
        SELECT MIN(id) 
        FROM monthlyExpense 
        GROUP BY monthly
    )
    """)
    suspend fun removeDuplicateMonthlyExpenses()
}