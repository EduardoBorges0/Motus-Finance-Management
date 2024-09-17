package com.app.simplemoney.Models.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.app.simplemoney6.Models.Room.DataClass.Expense

@Dao
interface Dao {
    @Query("SELECT * FROM table_banks")
    fun getAllBanks(): LiveData<List<Bank>>

    @Query("UPDATE table_banks SET balance = balance - :value WHERE id = :bankId")
    suspend fun decrementBalance(bankId: Int, value: Double)

    @Query("UPDATE table_banks SET balance = balance + :value WHERE id = :bankId")
    suspend fun incrementBalance(bankId: Int, value: Double)

    @Query("UPDATE table_banks SET date = :updatedDate WHERE id = :bankId")
    suspend fun updateBankDate(bankId: Int, updatedDate: String)


    @Query("SELECT SUM(balance) FROM table_banks")
    suspend fun getTotalBalance(): Double?

    @Insert
    suspend fun insertBank(bank: Bank)


    @Query("SELECT date FROM table_banks")
     fun getAllBankClosureDates(): List<String>

    @Delete
    suspend fun deleteBank(bank: Bank)

    @Update
    suspend fun updateBank(bank: Bank)

    @Query("SELECT * FROM table_banks WHERE id = :id LIMIT 1")
    suspend fun getBankById(id: Int): Bank?


}
