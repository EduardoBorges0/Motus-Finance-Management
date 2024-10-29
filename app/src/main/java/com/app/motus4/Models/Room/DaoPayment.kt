package com.app.motus4.Models.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.motus4.Models.Room.DataClass.ModelPayment
import com.app.motus4.Models.Room.DataClass.MonthlyExpense
import com.app.simplemoney.Models.Room.Bank

@Dao
interface DaoPayment {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(modelPayment: ModelPayment)

    @Query("DELETE FROM payment WHERE id= :id")
    suspend fun deletePayment(id: Long)

    @Update
    suspend fun updatePayment(payment: ModelPayment)

    @Query("SELECT * FROM payment WHERE id = :id LIMIT 1")
    suspend fun getPayment(id: Long) : ModelPayment?

    @Query("SELECT * FROM payment")
    suspend fun getPaymentAll() : ModelPayment?

}