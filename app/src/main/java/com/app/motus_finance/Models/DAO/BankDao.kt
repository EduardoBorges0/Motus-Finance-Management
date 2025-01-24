package com.app.motus_finance.Models.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.Entities.Banks

@Dao
interface BankDao {

    @Query("SELECT * FROM bank_entity")
    fun getAllBanks() : List<Banks>

    @Query("SELECT date FROM bank_entity")
    fun getAllDates() : List<String>

    @Query("SELECT date FROM bank_entity WHERE id = :id")
    fun getDatesById(id: Int) : String

    @Query("DELETE FROM bank_entity WHERE id= :id")
    suspend fun deleteBank(id: Int)

    @Query("UPDATE bank_entity SET sum = :sum")
    suspend fun updateAllSumToZero(sum: Double)

    @Query("UPDATE bank_entity SET date = :date WHERE id= :id")
    suspend fun updateDatePlusMonth(date: String, id: Int)

    @Insert
    suspend fun insertBank(banks: Banks)

    @Query("UPDATE bank_entity SET balance = :newBalance WHERE id = :bankId")
    suspend fun updateBalance(bankId: Int, newBalance: Double)

    @Query("UPDATE bank_entity SET date = :newDate WHERE id = :bankId")
    suspend fun updateBankDate(bankId: Int, newDate: String)

    @Query("UPDATE bank_entity SET sum = :sum WHERE id = :bankId")
    suspend fun updateSum(bankId: Int, sum: Double)

    @Query("SELECT SUM(sum) FROM bank_entity")
    suspend fun sumAllBank() : Double

}