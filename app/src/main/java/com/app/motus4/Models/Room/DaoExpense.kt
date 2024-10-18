package com.app.simplemoney8.Models.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.simplemoney6.Models.Room.DataClass.Expense
import java.time.LocalDate

@Dao
interface DaoExpense {

    @Query("SELECT * FROM table_expenses WHERE bankId = :id LIMIT 1")
    suspend fun getExpenseById(id: Int): Expense?

    @Query("DELETE FROM table_expenses WHERE bankId = :bankId AND type = :expenseType")
    suspend fun deleteExpenseByBankId(bankId: Int, expenseType: String)

    @Query("DELETE FROM table_expenses WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: Int)


    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT SUM(value) FROM table_expenses WHERE expenseClassification = :classification AND spentOrReceived = 'Spent'")
    suspend fun getTotalByClassification(classification: String): Double?

    @Query("SELECT SUM(value) FROM table_expenses WHERE spentOrReceived = 'Spent'")
    suspend fun getSpent() : Double?

    @Query("SELECT SUM(value) FROM table_expenses WHERE spentOrReceived = 'Received'")
    suspend fun getReceived() : Double?


    @Query("UPDATE table_expenses SET readyForDeletion = 1 WHERE bankId = :bankId AND type = 'Variable'")
    fun markExpensesReadyForDeletion(bankId: Int)

    @Query("DELETE FROM table_expenses WHERE readyForDeletion = 1")
    fun deleteExpensesReadyForDeletion()


    @Query("DELETE FROM table_expenses WHERE date = :currentDate")
    suspend fun deleteExpense(currentDate: String)


    @Query("SELECT SUM(value) FROM table_expenses WHERE spentOrReceived = 'Spent'")
    suspend fun getTotalSpent(): Double?

    @Query("SELECT SUM(value) FROM table_expenses WHERE spentOrReceived = 'Received'")
    suspend fun getTotalReceived(): Double?



    @Query("SELECT SUM(value) FROM table_expenses WHERE bankId = :bankId AND expenseClassification = :classification AND spentOrReceived = 'Spent'")
    suspend fun getTotalByClassificationAndBankId(bankId: Int, classification: String): Double?

    @Query("SELECT SUM(value) FROM table_expenses WHERE bankId = :bankId AND type = 'Variable' AND spentOrReceived = 'Spent'")
    suspend fun getTotalSpentForBank(bankId: Int): Double?

    @Query("SELECT SUM(value) FROM table_expenses WHERE bankId = :bankId AND spentOrReceived = 'Spent'")
    suspend fun getTotalSpentByBank(bankId: Int): Double?

    @Query("SELECT SUM(value) FROM table_expenses WHERE bankId = :bankId AND spentOrReceived = 'Received'")
    suspend fun getTotalReceivedByBank(bankId: Int): Double?

    @Query("SELECT * FROM table_expenses WHERE bankId = :bankId")
    fun getExpensesForBank(bankId: Int): LiveData<List<Expense>>

    @Query("SELECT * FROM table_expenses WHERE bankId = :bankId AND expenseClassification = :expenseClassification")
    fun getExpenseForClassification(bankId: Int, expenseClassification : String?): LiveData<List<Expense>>

    @Query("SELECT SUM(value) FROM table_expenses WHERE bankId = :bankId AND type = 'Variable' AND spentOrReceived = 'Received'")
    suspend fun getTotalReceivedForBank(bankId: Int): Double?

}