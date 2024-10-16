package com.app.simplemoney8.Models

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.app.simplemoney6.Models.Room.DataClass.Expense
import com.app.simplemoney8.Models.Room.DaoExpense
import java.time.LocalDate

class RepositoryExpense(private val daoExpense: DaoExpense) {

    fun getExpensesForBank(bankId: Int): LiveData<List<Expense>> = daoExpense.getExpensesForBank(bankId)

    suspend fun insertExpense(expense: Expense) {
        daoExpense.insertExpense(expense)
    }
    suspend fun getSpent() : Double? = daoExpense.getSpent()

    suspend fun getReceived() : Double? = daoExpense.getReceived()


    fun markExpensesReadyForDeletion(bankId: Int) = daoExpense.markExpensesReadyForDeletion(bankId)

    suspend fun deleteExpense(currentDate: String) = daoExpense.deleteExpense(currentDate)

    fun deleteExpensesReadyForDeletion() = daoExpense.deleteExpensesReadyForDeletion()

    suspend fun deleteExpenseById(expenseId: Int) = daoExpense.deleteExpenseById(expenseId)

    suspend fun getTotalSpentForBank(bankId: Int) : Double? = daoExpense.getTotalSpentForBank(bankId)

    suspend fun getTotalReceivedForBank(bankId: Int) : Double? = daoExpense.getTotalReceivedForBank(bankId)

    suspend fun getExpenseById(id: Int): Expense? = daoExpense.getExpenseById(id)

    suspend fun deleteExpenseByBankId(bankId : Int, expenseType : String){
        return daoExpense.deleteExpenseByBankId(bankId, expenseType)
    }

    suspend fun getTotalSpent(): Double? = daoExpense.getTotalSpent()

    suspend fun getTotalReceived(): Double? = daoExpense.getTotalReceived()


    suspend fun getTotalByClassification(classification: String): Double? = daoExpense.getTotalByClassification(classification)

    suspend fun getTotalByClassificationAndBankId(bankId: Int, classification: String): Double? = daoExpense.getTotalByClassificationAndBankId(bankId, classification)


    fun getExpenseForClassification(bankId: Int, expenseClassification : String?): LiveData<List<Expense>> = daoExpense.getExpenseForClassification(bankId, expenseClassification)

}