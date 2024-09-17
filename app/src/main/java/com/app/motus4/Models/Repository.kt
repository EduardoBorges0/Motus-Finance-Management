// Repository.kt
package com.app.simplemoney.Models

import androidx.lifecycle.LiveData
import com.app.simplemoney.Models.Room.Bank
import com.app.simplemoney.Models.Room.Dao
import com.app.simplemoney6.Models.Room.DataClass.Expense
import com.app.simplemoney8.Models.Room.DaoExpense

class Repository(private val dao: Dao) {

    fun getAllBanks(): LiveData<List<Bank>> = dao.getAllBanks()

     fun getAllBankClosureDates(): List<String> = dao.getAllBankClosureDates()

    suspend fun insertBank(bank: Bank) {
        dao.insertBank(bank)
    }
    suspend fun getTotalBalance(): Double?{
        return dao.getTotalBalance()
    }


    suspend fun deleteBank(bank: Bank) {
        dao.deleteBank(bank)
    }

    suspend fun updateBalanceForExpense(bankId: Int, value: Double, type: String) {
        if (type == "Spent") {
            dao.decrementBalance(bankId, value)
        } else if (type == "Received") {
            dao.incrementBalance(bankId, value)
        }
    }


    suspend fun updateExpenseDate(bankId: Int, updatedDate: String) = dao.updateBankDate(bankId, updatedDate)

    suspend fun updateBank(bank: Bank) {
        dao.updateBank(bank)
    }

    suspend fun getBankById(id: Int): Bank? {
        return dao.getBankById(id)
    }


}
