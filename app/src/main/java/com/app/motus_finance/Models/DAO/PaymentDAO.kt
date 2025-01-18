package com.app.motus_finance.Models.DAO

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PaymentDAO {

    @Query("UPDATE payment_table SET payment = :newPayment WHERE id = 1")
    suspend fun updatePayment(newPayment : Double) : Double

}