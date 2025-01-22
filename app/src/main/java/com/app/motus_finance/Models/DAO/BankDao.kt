package com.app.motus_finance.Models.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.motus_finance.Models.DTO.BankDTO

@Dao
interface BankDao {

    @Query("SELECT * FROM bank_entity")
    fun getAllBanks() : LiveData<List<BankDTO>>

    @Query("SELECT date FROM bank_entity")
    fun getAllDates() : LiveData<List<String>>

    @Insert
    suspend fun insertBank(bankDTO: BankDTO)

    @Query("UPDATE bank_entity SET balance = :newBalance WHERE id = :bankId")
    suspend fun updateBalance(bankId: Int, newBalance: Double)

    @Query("UPDATE bank_entity SET date = :newDate WHERE id = :bankId")
    suspend fun updateBankDate(bankId: Int, newDate: String) : String

    @Query("UPDATE bank_entity SET sum = :sum WHERE id = :bankId")
    suspend fun updateSum(bankId: Int, sum: Double) : BankDTO

}