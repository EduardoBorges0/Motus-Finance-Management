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

    @Insert
    suspend fun insertBank(bankDTO: BankDTO)

    @Query("UPDATE bank_entity SET balance = :newBalance WHERE id = :bankId")
    suspend fun updateBalance(bankId: Int, newBalance: Double)

}