package com.app.motus_finance.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.motus_finance.data.models.Market

@Dao
interface MarketDAO {

    @Query("SELECT * FROM market_entity")
    fun getAllBanks() : LiveData<List<Market>>

    @Query("SELECT date FROM market_entity")
    fun getAllDates() : List<String>

    @Query("SELECT date FROM market_entity WHERE id = :id")
    fun getDatesById(id: Int) : String

    @Query("DELETE FROM market_entity WHERE id= :id")
    suspend fun deleteBank(id: Int)

    @Query("UPDATE market_entity SET sum = :sum")
    suspend fun updateAllSumToZero(sum: Double)

    @Query("UPDATE market_entity SET date = :date WHERE id= :id")
    suspend fun updateDatePlusMonth(date: String, id: Int)

    @Insert
    suspend fun insertBank(banks: Market)

    @Query("UPDATE market_entity SET balance = :newBalance WHERE id = :bankId")
    suspend fun updateBalance(bankId: Int, newBalance: Double)

    @Query("SELECT balance FROM market_entity WHERE id = :id")
    suspend fun getBalanceById(id: Int) : Double

    @Query("UPDATE market_entity SET date = :newDate WHERE id = :bankId")
    suspend fun updateBankDate(bankId: Int, newDate: String)

    @Query("UPDATE market_entity SET sum = :sum WHERE id = :bankId")
    suspend fun updateSum(bankId: Int, sum: Double)

    @Query("SELECT SUM(sum) FROM market_entity")
    suspend fun sumAllBank() : Double

}